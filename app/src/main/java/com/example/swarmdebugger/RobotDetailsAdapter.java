package com.example.swarmdebugger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Alex on 10/04/2017.
 */

public class RobotDetailsAdapter extends ArrayAdapter {

    List<DebugInfo> debugInfoList;

    Activity context;

    static class ViewHolder {
        public TextView debugInfo;
    }



    public RobotDetailsAdapter(Activity context, List<DebugInfo> debugInfoList) {
        super(context, R.layout.robot_list_row, debugInfoList);
        this.debugInfoList = debugInfoList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.robot_details_row, null);

            // configure view holder
            RobotDetailsAdapter.ViewHolder viewHolder = new RobotDetailsAdapter.ViewHolder();
            viewHolder.debugInfo = (TextView) rowView.findViewById(R.id.debug_info);
            rowView.setTag(viewHolder);
        }

        // fill data
        RobotDetailsAdapter.ViewHolder holder = (RobotDetailsAdapter.ViewHolder) rowView.getTag();

        String s = debugInfoList.get(position).getName() + ": " + debugInfoList.get(position).getVal();
        holder.debugInfo.setText(s);

        return rowView;
    }




}
