package com.example.swarmdebugger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 31/03/2017.
 */

public class RobotListAdapter extends ArrayAdapter {

    Activity context;
    List<Robot> robotList;

    static class ViewHolder {
        public TextView robotId;
        public TextView robotStatus;
    }

    public RobotListAdapter(Activity context, List<Robot> robotList) {
        super(context, R.layout.robot_list_row, robotList);
        this.context = context;
        this.robotList = robotList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.robot_list_row, null);

            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.robotId = (TextView) rowView.findViewById(R.id.robot_id);
            viewHolder.robotStatus = (TextView) rowView.findViewById(R.id.robot_status);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        // Set the robot id and status lines
        String s = "Robot " + robotList.get(position).getId();
        holder.robotId.setText(s);
        s = "Status: " + robotList.get(position).getStatus();
        holder.robotStatus.setText(s);

        return rowView;
    }
}
