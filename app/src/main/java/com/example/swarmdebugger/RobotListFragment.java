package com.example.swarmdebugger;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.swarmdebugger.R.styleable.View;

/**
 * Created by Alex on 31/03/2017.
 */

public class RobotListFragment extends ListFragment {

    OnRobotSelectedListener mCallback;

    RobotListAdapter adapter;

    // Container Activity must implement this interface
    public interface OnRobotSelectedListener {
        public void onRobotSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnRobotSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRobotSelectedListener");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.robot_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new RobotListAdapter(getActivity(), MainActivity.robotList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mCallback.onRobotSelected(position);
    }

    /* Replaces current fragment with a new robot details fragment */
    private void showRobotDetails(int position) {

    }

    public RobotListAdapter getAdapter() {
        return adapter;
    }

}
