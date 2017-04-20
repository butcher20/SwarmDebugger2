package com.example.swarmdebugger;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 31/03/2017.
 */

public class RobotDetailsFragment extends ListFragment {

    Robot robot;
    List<View> debugInfoViews;
    LinearLayout layout;

    TextView id, status, posX, posY;

    RobotDetailsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.robot_details, container, false);

        int index = MainActivity.getRobotIndexFromId((getArguments().getInt("id")));

        robot = MainActivity.robotList.get(index);


        TextView id = (TextView) view.findViewById(R.id.robot_details_id);
        status = (TextView) view.findViewById(R.id.robot_details_status);
        posX = (TextView) view.findViewById(R.id.robot_posx);
        posY = (TextView) view.findViewById(R.id.robot_posy);


        String s = "Robot " + robot.getId();
        id.setText(s);
        s = "Status: " + robot.getStatus();
        status.setText(s);
        s = "Pos X: " + robot.getPosX();
        posX.setText(s);
        s = "pos Y: " + robot.getPosY();
        posY.setText(s);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new RobotDetailsAdapter(getActivity(), robot.getDebugInfo());
        setListAdapter(adapter);
    }

    private List<View> populateDebugInfoViews() {
        List<View> viewList= new ArrayList<View>();
        List<DebugInfo> debugInfoList = robot.getDebugInfo();
        String s;
        DebugInfo debugInfo;

        //Loop through the list of debug infos and create a view for each one
        for(int i = 0; i<debugInfoList.size(); i++) {
            debugInfo = debugInfoList.get(i);
            TextView textView = new TextView(getActivity());
            s = debugInfo.getName() + ": " + debugInfo.getVal();
            textView.setText(s);

            viewList.add(textView);
        }
        return viewList;
    }

    public Robot getRobot() {
        return robot;
    }

    public TextView getStatus() {
        return status;
    }

    public TextView getPosX() {
        return posX;
    }

    public TextView getPosY() {
        return posY;
    }
}
