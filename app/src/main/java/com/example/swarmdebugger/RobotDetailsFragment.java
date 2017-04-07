package com.example.swarmdebugger;

import android.app.Fragment;
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

public class RobotDetailsFragment extends Fragment {

    Robot robot;
    List<View> debugInfoViews;
    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        robot = MainActivity.robotList.get(getArguments().getInt("index"));
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.robot_details, container, false);

        layout = (LinearLayout) view.findViewById(R.id.robot_details);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView id = new TextView(getActivity());
        TextView status = new TextView(getActivity());
        TextView posX = new TextView(getActivity());
        TextView posY = new TextView(getActivity());


        String s = "Robot " + robot.getId();
        id.setText(s);
        s = "Status: " + robot.getStatus();
        status.setText(s);
        s = "Pos X: " + robot.getPosX();
        posX.setText(s);
        s = "pos Y: " + robot.getPosY();
        posY.setText(s);
        //Add all the views to the layout
        layout.addView(id);
        layout.addView(status);
        layout.addView(posX);
        layout.addView(posY);

        //If additional debug info exists, retrieve and print it.
        if (robot.getDebugInfo() != null) {
            debugInfoViews = getDebugInfoViews();
            for (int i=0; i<debugInfoViews.size(); i++) {
                layout.addView(debugInfoViews.get(i));
            }
        }
        return view;

    }

    private List<View> getDebugInfoViews() {
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
}
