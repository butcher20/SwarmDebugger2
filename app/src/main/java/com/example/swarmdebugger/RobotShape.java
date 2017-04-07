package com.example.swarmdebugger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 05/04/2017.
 */

public class RobotShape extends View {

    int posX;
    int posY;
    int textSize = 20;
    int radius = 20;

    //Distance between circle centre and bottom of text
    int textOffset= radius;
    //Distance between lines of text
    int textGap = 5;
    int textStart;

    Robot robot;

    List<String> robotText = new ArrayList<String>();

    Paint paint;
    Paint textPaint;

    public RobotShape(Context context, Robot robot) {
        super(context);
        this.robot = robot;



        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

    }

    @Override
    public void onDraw (Canvas canvas) {
        super.onDraw(canvas);

        posX = robot.getPosX()+ArenaFragment.arenaMargin;
        posY = robot.getPosY()+ArenaFragment.arenaMargin;

        canvas.drawCircle(posX, posY, radius, paint);

        String s;
        //Check if robot ID should be shown as text
        if(robot.showId) {
            s = "ID: " + robot.getId();
            robotText.add(s);
        }
        //Check if status statement should be displayed
        if (robot.showStatus) {
            s = robot.getStatus();
            robotText.add(s);
        }
        //Check if other debug info should be displayed as text
        if (robot.getDebugInfo().size() > 0) {
            DebugInfo debugInfo;
            for (int i = 0; i < robot.getDebugInfo().size();i++ ) {
                debugInfo = robot.getDebugInfo().get(i);
                if (debugInfo.displayAsText) {
                    s = debugInfo.getName() + ": " + debugInfo.getVal();
                    robotText.add(s);
                }
            }
        }

        //Draw all the text above the robot circle
        int textItems = robotText.size();
        textStart = posY - textOffset - (textSize*textItems) - (textGap*(textItems-1));
        for (int i = 0; i < robotText.size(); i++) {
            canvas.drawText(robotText.get(i), posX, textStart, textPaint);
            textStart = textStart + textSize + textGap;
        }

        robotText.clear();

    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}
