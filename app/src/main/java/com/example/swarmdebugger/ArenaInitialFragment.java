package com.example.swarmdebugger;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Alex on 03/04/2017.
 */

public class ArenaInitialFragment extends Fragment {
    OnButtonPressedListener mCallback;
    int fragWidth;
    int fragHeight;

    // Container Activity must implement this interface
    public interface OnButtonPressedListener {
        public void onArenaButtonPressed(int x, int y, int fragWidth, int fragHeight);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ArenaInitialFragment.OnButtonPressedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRobotSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.arena_initial, container,false);

        final EditText arenaX = (EditText)view.findViewById(R.id.arena_x);
        final EditText arenaY = (EditText)view.findViewById(R.id.arena_y);

        final Button button = (Button)view.findViewById(R.id.arena_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check text field contain data.
                if ((arenaX.getText() != null) && (arenaY.getText()!= null)) {
                    int x = Integer.parseInt(arenaX.getText().toString());
                    int y = Integer.parseInt(arenaY.getText().toString());
                    mCallback.onArenaButtonPressed(x, y, fragWidth, fragHeight);
                }
            }
        });

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                fragWidth = view.getWidth();
                fragHeight = view.getHeight();
            }
        });


        return view;
    }
}
