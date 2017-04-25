package com.example.swarmdebugger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Alex on 10/04/2017.
 */

public class RobotDetailsAdapter extends ArrayAdapter {

    List<DebugInfo> debugInfoList;

    Activity context;

    Robot robot;


    static class ViewHolder {
        TextView debugInfo;
        CheckBox textCheckBox;
        CheckBox graphicCheckBox;
    }



    public RobotDetailsAdapter(Activity context, List<DebugInfo> debugInfoList, Robot robot) {
        super(context, R.layout.robot_list_row, debugInfoList);
        this.debugInfoList = debugInfoList;
        this.context = context;
        this.robot = robot;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.robot_details_row, null);

            // configure view holder
            final RobotDetailsAdapter.ViewHolder viewHolder = new RobotDetailsAdapter.ViewHolder();
            viewHolder.debugInfo = (TextView) rowView.findViewById(R.id.debug_info);
            viewHolder.graphicCheckBox = (CheckBox)rowView.findViewById(R.id.debug_as_graphic);
            viewHolder.graphicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DebugInfo checkedItem = (DebugInfo) viewHolder.graphicCheckBox.getTag();
                    checkedItem.setDisplayAsGraphic(buttonView.isChecked());
                    RobotDetailsFragment.checkBoxCallback.onCheckBoxClicked(robot);
                }
            });
            viewHolder.textCheckBox = (CheckBox)rowView.findViewById(R.id.debug_as_text);
            viewHolder.textCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DebugInfo checkedItem = (DebugInfo) viewHolder.graphicCheckBox.getTag();
                    checkedItem.setDisplayAsText(buttonView.isChecked());
                    RobotDetailsFragment.checkBoxCallback.onCheckBoxClicked(robot);
                }
            });
            rowView.setTag(viewHolder);
            viewHolder.graphicCheckBox.setTag(debugInfoList.get(position));
            viewHolder.textCheckBox.setTag(debugInfoList.get(position));
        } else {
            ((ViewHolder) rowView.getTag()).graphicCheckBox.setTag(debugInfoList.get(position));
            ((ViewHolder) rowView.getTag()).textCheckBox.setTag(debugInfoList.get(position));
        }

        // fill data
        RobotDetailsAdapter.ViewHolder holder = (RobotDetailsAdapter.ViewHolder) rowView.getTag();

        String s = debugInfoList.get(position).getName() + ": " + debugInfoList.get(position).getVal();
        holder.debugInfo.setText(s);

        holder.textCheckBox.setChecked(debugInfoList.get(position).isDisplayAsText());
        //Only display graphics checkbox for IR sensors.
        if (debugInfoList.get(position).isCanBeDisplayedAsGraphic()) {
            holder.graphicCheckBox.setVisibility(View.VISIBLE);
            holder.graphicCheckBox.setChecked(debugInfoList.get(position).displayAsGraphic);
        }

        return rowView;
    }




}
