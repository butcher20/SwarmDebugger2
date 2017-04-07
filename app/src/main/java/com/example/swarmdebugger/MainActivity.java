package com.example.swarmdebugger;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RobotListFragment.OnRobotSelectedListener,
                                                                ArenaInitialFragment.OnButtonPressedListener,
                                                                    RobotPacketReader.OnPacketProcessedListener {
    static List<Robot> robotList = new ArrayList<Robot>();
    DebugInfo info, info2, info3;
    List<DebugInfo> infoList, infoList2;
    ServerThread serverThread;

    ArenaFragment arenaFragment;

    static final int PORT = 6666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        robotList.add(new Robot(1, "Moving", 500, 200));
        robotList.add(new Robot(2, "Stationary", 20, 65));
        robotList.add(new Robot(3, "Moving", 102, 76));

        info = new DebugInfo("Light", 30);
        info2 = new DebugInfo("Dark", 60);
        info3 = new DebugInfo("Huh", 9999);

        infoList = new ArrayList<DebugInfo>();
        infoList2 = new ArrayList<DebugInfo>();
        infoList.add(info);
        infoList.add(info2);
        infoList2.add(info3);


        robotList.get(0).setDebugInfo(infoList);
        robotList.get(2).setDebugInfo(infoList2);

        // Create a new Fragment to be placed in the activity layout
        RobotListFragment robotListFragment = new RobotListFragment();

        // Add the fragment to the 'fragment_container' FrameLayout
        getFragmentManager().beginTransaction().add(R.id.fragment_container, robotListFragment, "robot_list")
                .commit();

        // Create a new Fragment to be placed in the activity layout
        ArenaInitialFragment arenaInitialFragment = new ArenaInitialFragment();

        // Add the fragment to the 'fragment_container' FrameLayout
        getFragmentManager().beginTransaction().add(R.id.arena_container, arenaInitialFragment)
                .commit();

        final Button button = (Button) findViewById(R.id.receive_button);
        button.setTag(1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status =(Integer) v.getTag();
                if (status == 1) {
                    serverThread = new ServerThread(PORT, (RobotPacketReader.OnPacketProcessedListener) MainActivity.this);
                    serverThread.setRunning(true);
                    serverThread.start();
                    button.setText(R.string.stop_receive_data);
                    v.setTag(0);
                } else {
                    serverThread.setRunning(false);
                    button.setText(R.string.receive_data);
                    v.setTag(1);
                }

            }
        });
    }

    public void onRobotSelected(int position) {
        // Create new fragment and transaction
        RobotDetailsFragment newFragment = new RobotDetailsFragment();

        Bundle args = new Bundle();
        args.putInt("index", position);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, newFragment, "robot_details");
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onArenaButtonPressed(int x, int y, int fragWidth, int fragHeight) {
        // Create new fragment and transaction
        arenaFragment = new ArenaFragment();

        Bundle args = new Bundle();
        args.putInt("x", x);
        args.putInt("y", y);
        args.putInt("fragWidth", fragWidth);
        args.putInt("fragHeight", fragHeight);
        arenaFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.arena_container, arenaFragment);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onPacketProcessed(Robot robot) {
        int i;
        //Go through the list of robots in the arena until the corresponding robot ID is found
        for (i = 0; i < arenaFragment.getActiveRobots().size(); i++) {
            if (arenaFragment.getActiveRobots().get(i).getRobot().getId() != robot.getId()) {
                //Insert the updated robot information into the robot arena object
                arenaFragment.getActiveRobots().get(i).setRobot(robot);
                //Invalidate the view to redraw it
                arenaFragment.getActiveRobots().get(i).invalidate();
                break;
            }
        }
        // If robot id was not found in the list then add it to the end of the list.
        if (i == arenaFragment.getActiveRobots().size()) {
            // Create a new robot entity for the arena
            RobotShape robotShape = new RobotShape(this, robot);
            // Add entity to list of entitites in the arena
            arenaFragment.getActiveRobots().add(robotShape);
            // Add the robot shape to the layout.
            arenaFragment.getLayout().addView(robotShape);
        }

        //Update the list if list fragement is active
        RobotListFragment fragment = (RobotListFragment) getFragmentManager().findFragmentByTag("robot_list");
        if(fragment != null && fragment.isVisible()) {
            fragment.getAdapter().notifyDataSetChanged();
        } else {

            //Active fragment is the robot details fragment
            RobotDetailsFragment detailsFragment = (RobotDetailsFragment) getFragmentManager().findFragmentByTag("robot_details");
            // Check if fragment displays information for the robot that has just been updated
            if (detailsFragment.getRobot().getId() == robot.getId()){
                //Refresh the fragment to show the updated information
                // Need to pass the fragment the index of the robot in the list
                i = 0;
                while (robotList.get(i).getId() != robot.getId()) {
                    i++;
                }

                Bundle args = new Bundle();
                args.putInt("index", i);
                detailsFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the fragment with itself to force it to redraw
                transaction.replace(R.id.fragment_container, detailsFragment, "robot_details");

                // Commit the transaction
                transaction.commit();
            }
        }
    }
}
