package com.example.swarmdebugger;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RobotListFragment.OnRobotSelectedListener,
                                                                ArenaInitialFragment.OnButtonPressedListener,
                                                                    ServerThread.OnPacketReceivedListener,  RobotDetailsFragment.OnCheckBoxChangeListener {
    static List<Robot> robotList = new ArrayList<Robot>();
    DebugInfo info, info2, info3, info4, info5, info6, info7, info8;
    DebugInfo infoa, infob, infoc, infod, infoe, infof, infog, infoh;
    List<DebugInfo> infoList, infoList2;
    ServerThread serverThread;

    ArenaFragment arenaFragment;

    static final int PORT = 6666;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        robotList.add(new Robot(1, "Moving", 50, 50, 90));
        robotList.add(new Robot(2, "Stationary", 0, 25, 270));
        robotList.add(new Robot(3, "Moving", -50, 50, 215));

        info = new DebugInfo("IR0", 0);
        info2 = new DebugInfo("IR1", 500);
        info3 = new DebugInfo("IR2", 1000);
        info4 = new DebugInfo("IR3", 1500);
        info5 = new DebugInfo("IR4", 2000);
        info6 = new DebugInfo("IR5", 2500);
        info7 = new DebugInfo("IR6", 3000);
        info8 = new DebugInfo("IR7", 4000);

        infoa = new DebugInfo("info 1", 100);
        infob = new DebugInfo("info 2", 420);
        infoc = new DebugInfo("info 3", 666);
        infod = new DebugInfo("info 4", 9999);
        infoe = new DebugInfo("info 5", 0);
        infof = new DebugInfo("info 6", 4300);
        infog = new DebugInfo("info 7", 80);
        infoh = new DebugInfo("info 8", 42);

        info.setCanBeDisplayedAsGraphic(true);
        info2.setCanBeDisplayedAsGraphic(true);
        info3.setCanBeDisplayedAsGraphic(true);
        info4.setCanBeDisplayedAsGraphic(true);
        info5.setCanBeDisplayedAsGraphic(true);
        info6.setCanBeDisplayedAsGraphic(true);
        info7.setCanBeDisplayedAsGraphic(true);
        info8.setCanBeDisplayedAsGraphic(true);




        infoList = new ArrayList<DebugInfo>();
        infoList2 = new ArrayList<DebugInfo>();
        infoList.add(info);
        infoList.add(info2);
        infoList.add(info3);
        infoList.add(info4);
        infoList.add(info5);
        infoList.add(info6);
        infoList.add(info7);
        infoList.add(info8);
        infoList2.add(infoa);
        infoList2.add(infob);
        infoList2.add(infoc);
        infoList2.add(infod);
        infoList2.add(infoe);
        infoList2.add(infof);
        infoList2.add(infog);
        infoList2.add(infoh);


        robotList.get(0).setInfraRedSensor(infoList);
        robotList.get(0).setDebugInfo(infoList2);
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
                    Log.d("server", "Creating socket");
                    serverThread = new ServerThread(PORT, (ServerThread.OnPacketReceivedListener) MainActivity.this);
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

        args.putInt("id", robotList.get(position).getId());
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
        transaction.replace(R.id.arena_container, arenaFragment, "arena");

        // Commit the transaction
        transaction.commit();
    }


    static public int getRobotIndexFromId(int id) {
        Robot robot;
        if (robotList.size() > 0) {
            for (int i = 0; i < robotList.size(); i++) {
                if (robotList.get(i).getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    static public Robot getRobotAndIndexFromId(int id, int index) {
        Robot robot;
        if (robotList.size() > 0) {
            for (int i = 0; i < robotList.size(); i++) {
                robot = robotList.get(i);
                if (robot.getId() == id) {
                    index = i;
                    return robot;
                }
            }
        }
        return null;
    }

    @Override
    public void onPacketReceived(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RobotPacketReader task = new RobotPacketReader(context);
                task.execute(new String[] { data });
            }
        });
    }

    @Override
    public void onCheckBoxClicked(Robot robot) {
        //Check if arena fragment is active
        ArenaFragment fragment = (ArenaFragment) getFragmentManager().findFragmentByTag("arena");
        if (fragment != null && fragment.isVisible()) {
            //Redraw the robot in the arena whose checkbox information has changed
            for (int i = 0; i < arenaFragment.getActiveRobots().size(); i++) {
                if (arenaFragment.getActiveRobots().get(i).getRobot().getId() == robot.getId()) {
                    //Insert the updated robot information into the robot arena object
                    arenaFragment.getActiveRobots().get(i).setRobot(robot);
                    //Invalidate the view to redraw it
                    arenaFragment.getActiveRobots().get(i).invalidate();
                    break;
                }
            }
        }
    }

    private class RobotPacketReader extends AsyncTask<String, Void, Robot> {

        DatagramPacket packet;
        Reader reader;
        StringBuilder builder;
        Robot robot;
        Boolean newRobot = false;
        int index, c;

        Context context;

        protected RobotPacketReader (Context context) {
            this.context = context;
        }

        @Override
        protected Robot doInBackground(String... params) {

            reader = new StringReader(params[0]);
            builder = new StringBuilder();

            try {
                c = reader.read();

                // Get robot ID
                while (c != '#') {
                    builder.append((char) c);
                    c = reader.read();
                }
                int id = Integer.parseInt(builder.toString());

                // Check if robot already exists in robot list.
                if ((index = MainActivity.getRobotIndexFromId(id)) == -1) {
                    // no matching robot in list so create a new Robot object
                    robot = new Robot(id);
                    newRobot = true;
                } else {
                    robot = robotList.get(index);
                }

                //Process the remaing debug information
                builder.setLength(0);
                c = reader.read();
                //continue until end of string
                while (c != -1) {
                    // Get item name.
                    while (c != ':') {
                        builder.append((char) c);
                        c = reader.read();
                    }
                    //Check if extracted element was the status
                    if (builder.toString().equals("status")) {
                        readData();
                        robot.setStatus(builder.toString());
                    } else if (builder.toString().equals("xPos")) {
                        readData();
                        robot.setPosX(Integer.parseInt(builder.toString()));
                    } else if (builder.toString().equals("yPos")) {
                        readData();
                        robot.setPosY(Integer.parseInt(builder.toString()));
                    } else {

                        //Custom debug info
                        // Name of the custom debug info
                        String name = builder.toString();
                        //Read the value of the custom debug info
                        readData();
                        int i;
                        //Check to see whether the data is infra red data.
                        if ((name.length() == 3) && name.startsWith("IR")) {
                            for (i = 0; i < robot.getInfraRedSensor().size(); i++) {
                                if (robot.getInfraRedSensor().get(i).getName().equals(name)) {
                                    // IR sensor info exists so update value
                                    robot.getInfraRedSensor().get(i).setVal(Integer.parseInt(builder.toString()));
                                    break;
                                }
                            }
                            // No existing debug info
                            if (i == robot.getInfraRedSensor().size()) {
                                // Create a new DebugInfo object with the extracted name
                                DebugInfo debugInfo = new DebugInfo(name);
                                // Set the value in the DebugInfo object
                                debugInfo.setVal(Integer.parseInt(builder.toString()));
                                //Add the infrared data to the list in the robot object
                                robot.getInfraRedSensor().add(debugInfo);
                            }
                        } else {
                            // Custom debug info (not IR sensor info)
                            // Check if DebugInfo object already exists for received debug info.
                            for (i = 0; i < robot.getDebugInfo().size(); i++) {
                                if (robot.getDebugInfo().get(i).getName().equals(name)) {
                                    // Debug info exists so update value
                                    robot.getDebugInfo().get(i).setVal(Integer.parseInt(builder.toString()));
                                    break;
                                }
                            }
                            // No existing debug info
                            if (i == robot.getDebugInfo().size()) {
                                // Create a new DebugInfo object with the extracted name
                                DebugInfo debugInfo = new DebugInfo(name);
                                // Set the value in the DebugInfo object
                                debugInfo.setVal(Integer.parseInt(builder.toString()));
                                //Add the debuginfo to the list in the robot object
                                robot.getDebugInfo().add(debugInfo);
                            }
                        }
                    }
                    builder.setLength(0);
                    c = reader.read();
                }
                // Entire string processed so either update a robot in the robotlist or add it to the list.
                if (newRobot) {
                    // New robot so add to the end of list
                    MainActivity.robotList.add(robot);
                } else {
                    // Existing robot so update robot information in list
                    MainActivity.robotList.set(index, robot);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return robot;
        }

        @Override
        protected void onPostExecute(Robot robot) {
            //Update the list if list fragement is active
            RobotListFragment fragment = (RobotListFragment) getFragmentManager().findFragmentByTag("robot_list");
            if (fragment != null && fragment.isVisible()) {
                fragment.getAdapter().notifyDataSetChanged();

            }else {
                //Active fragment is the robot details fragment
                RobotDetailsFragment detailsFragment = (RobotDetailsFragment) getFragmentManager().findFragmentByTag("robot_details");
                if (detailsFragment != null && detailsFragment.isVisible()) {
                    // Check if fragment displays information for the robot that has just been updated
                    if (detailsFragment.getRobot().getId() == robot.getId()) {

                        // Redraw the sttaus, posx and posy parameters
                        detailsFragment.getStatus().invalidate();
                        detailsFragment.getPosX().invalidate();
                        detailsFragment.getPosY().invalidate();

                        fragment.getAdapter().notifyDataSetChanged();
                        //Refresh the fragment to show the updated information

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace the fragment with itself to force it to redraw
                        transaction.replace(R.id.fragment_container, detailsFragment, "robot_details");

                        // Commit the transaction
                        transaction.commit();
                    }
                }
            }
            //Go through the list of robots in the arena until the corresponding robot ID is found

            ArenaFragment arenaFragment = (ArenaFragment) getFragmentManager().findFragmentByTag("arena");
            int i;
            for (i = 0; i < arenaFragment.getActiveRobots().size(); i++) {
                if (arenaFragment.getActiveRobots().get(i).getRobot().getId() == robot.getId()) {
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
                RobotShape robotShape = new RobotShape(context, robot, arenaFragment.getBorder().getPixelWidth(), arenaFragment.getBorder().getPixelWidth());
                // Add entity to list of entitites in the arena
                arenaFragment.getActiveRobots().add(robotShape);
                // Add the robot shape to the layout.
                arenaFragment.getLayout().addView(robotShape);
            }
        }


        private void readData() throws IOException {
            builder.setLength(0);
            c = reader.read();
            while (c != '#') {
                builder.append((char) c);
                c = reader.read();
            }
        }

    }

}
