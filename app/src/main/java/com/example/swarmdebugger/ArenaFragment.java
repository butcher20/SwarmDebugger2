package com.example.swarmdebugger;

import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Alex on 03/04/2017.
 */

public class ArenaFragment extends Fragment {

    static int screenHeight;
    static int screenWidth;

    static int arenaFragWidth;
    static int arenaFragHeight;

    static int arenaMargin = 16;

    static int arenaX;
    static int arenaY;

    RelativeLayout layout;

    List<RobotShape> activeRobots = new ArrayList<RobotShape>();
    Canvas canvas = new Canvas();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arenaX = getArguments().getInt("x");
        arenaY = getArguments().getInt("y");
        arenaFragWidth = getArguments().getInt("fragWidth");
        arenaFragHeight = getArguments().getInt("fragHeight");
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.arena_layout, container, false);

        layout = (RelativeLayout)view.findViewById(R.id.arena);


        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                setScreenSize();

                ArenaBorder border = new ArenaBorder(getActivity(), arenaX, arenaY);

                layout.addView(border);

                if (MainActivity.robotList.size() > 0) {
                    for (int i = 0; i < MainActivity.robotList.size(); i++) {
                        RobotShape robotShape = new RobotShape(getActivity(), MainActivity.robotList.get(i));
                        activeRobots.add(robotShape);
                        layout.addView(robotShape);
                    }
                }

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setScreenSize() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    public List<RobotShape> getActiveRobots() {
        return activeRobots;
    }

    public RelativeLayout getLayout() {
        return layout;
    }

}
