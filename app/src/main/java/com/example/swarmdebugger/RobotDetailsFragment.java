package com.example.swarmdebugger;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    TextView id, status, posX, posY;
    CheckBox idCheckBox, statusCheckBox, posXCheckBox, posYCheckBox;

    RobotDetailsAdapter irAdapter;
    RobotDetailsAdapter debugAdapter;

    ListView irList;
    ListView debugList;

    static OnCheckBoxChangeListener checkBoxCallback;

    // Container Activity must implement this interface
    public interface OnCheckBoxChangeListener {
        public void onCheckBoxClicked(Robot robot);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            checkBoxCallback = (RobotDetailsFragment.OnCheckBoxChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCheckBoxChangeListener");
        }
    }


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

        irList = (ListView) view.findViewById(R.id.ir_list);
        debugList = (ListView) view.findViewById(R.id.debug_list);

        //Set checkbox states and set up listener methods for them
        idCheckBox = (CheckBox)view.findViewById(R.id.id_checkbox);
        statusCheckBox = (CheckBox)view.findViewById(R.id.status_checkbox);
        posXCheckBox = (CheckBox)view.findViewById(R.id.posx_checkbox);
        posYCheckBox = (CheckBox)view.findViewById(R.id.posy_checkbox);
        idCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                robot.setShowId(buttonView.isChecked());
                checkBoxCallback.onCheckBoxClicked(robot);
            }
        });
        statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                robot.setShowStatus(buttonView.isChecked());
                checkBoxCallback.onCheckBoxClicked(robot);
            }
        });
        posXCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                robot.setShowPosX(buttonView.isChecked());
                checkBoxCallback.onCheckBoxClicked(robot);
            }
        });
        posYCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                robot.setShowPosY(buttonView.isChecked());
                checkBoxCallback.onCheckBoxClicked(robot);
            }
        });

        idCheckBox.setChecked(robot.isShowId());
        statusCheckBox.setChecked(robot.isShowStatus());
        posXCheckBox.setChecked(robot.isShowPosX());
        posYCheckBox.setChecked(robot.isShowPosY());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        irAdapter = new RobotDetailsAdapter(getActivity(), robot.getInfraRedSensor(), robot);
        debugAdapter = new RobotDetailsAdapter(getActivity(), robot.getDebugInfo(), robot);
        irList.setAdapter(irAdapter);
        debugList.setAdapter(debugAdapter);
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
