package com.example.swarmdebugger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by Alex on 05/04/2017.
 */

public class RobotShape extends View {

    ArenaBorder border;
    int arenaWidth;
    int arenaHeight;

    int posX;
    int posY;
    int textSize = 32;
    int radius = 35;

    //Distance between circle centre and bottom of text
    int textOffset= radius;
    //Distance between lines of text
    int textGap = 5;
    int textStart;

    // Direction Arrow variables
    int halfShaftLength = 30; // Should be a bit less than the circle radius
    int arrowTip;
    Path arrowHead;
    int arrowHeadLength = 10;
    int arrowHeadWidth = 10; // Width each side if the shaft

    // Distance between circle circumference and beginning of IR lines
    int numIRSensors = 8;
    int irLineOffset = 5;
    int irLineStart = radius + irLineOffset;
    int[] irAngles = {17, 48, 90, 30, -30, -90, -48, -17};
    int[] irCosCoeffs = {-1, -1, -1, 1, 1, -1, -1, -1};
    int irLineMaxLength = 80;
    int irMaxValue = 4000;
    double irRadians;

    int irStartX;
    int irStartY;
    int irEndX;
    int irEndY;
    int irLineLength;
    float irLineLengthRatio;

    Robot robot;

    List<String> robotText = new ArrayList<String>();

    Paint paint;
    Paint textPaint;
    Paint irPaint;
    Paint arrowPaint;

    public RobotShape(Context context, Robot robot, int arenaWidth, int arenaHeight) {
        super(context);
        this.robot = robot;

        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;



        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(3);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        irPaint = new Paint();
        irPaint.setColor(Color.RED);
        irPaint.setStrokeWidth(3);

        arrowPaint = new Paint();
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setColor(Color.BLACK);
        arrowPaint.setStrokeWidth(5);

        arrowHead = new Path();


    }

    @Override
    public void onDraw (Canvas canvas) {
        super.onDraw(canvas);


      //  posX = robot.getPosX()+ArenaFragment.arenaMargin;
       // posY = robot.getPosY()+ArenaFragment.arenaMargin;

        if (robot.getPosX() >= 0) {
            posX = ArenaFragment.arenaMargin + (arenaWidth / 2) + (int) ((arenaWidth/2) * ((float) robot.getPosX()/100));
        } else {
            posX = ArenaFragment.arenaMargin + (int)((arenaWidth/2) * ((float)(100+robot.getPosX())/100));
        }
        posY = ArenaFragment.arenaMargin + (int) (arenaHeight * ((float)(100-robot.getPosY())/100));

        //Draw the circle which represents the robot.
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
        //Check if PosX should be displayed
        if (robot.showPosX) {
            s = "PosX: " + robot.getPosX();
            robotText.add(s);
        }
        //Check if PosY should be displayed
        if (robot.showPosY) {
            s = "PosY: " + robot.getPosX();
            robotText.add(s);
        }

        //Check if infrared sensors should be displayed as text
        if (robot.getInfraRedSensor().size() > 0) {
            DebugInfo debugInfo;
            for (int i = 0; i < robot.getDebugInfo().size();i++ ) {
                debugInfo = robot.getInfraRedSensor().get(i);
                if (debugInfo.displayAsText) {
                    s = debugInfo.getName() + ": " + debugInfo.getVal();
                    robotText.add(s);
                }
            }
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

        canvas.rotate(robot.getOrientation(), posX, posY);

        // Draw direction arrow
        // Arrow Shaft
        arrowTip = posY-halfShaftLength;
        canvas.drawLine(posX, posY+halfShaftLength, posX, arrowTip, arrowPaint);
        // Arrow Head
        arrowHead.moveTo(posX, arrowTip);
        arrowHead.lineTo(posX-arrowHeadWidth, arrowTip+arrowHeadLength);
        arrowHead.lineTo(posX+arrowHeadWidth, arrowTip+arrowHeadLength);
        arrowHead.close();
        canvas.drawPath(arrowHead, arrowPaint);


        if (robot.getInfraRedSensor().size()>0) {
            for (int i = 0; i < numIRSensors; i++) {
                //Check if infrared line should be displayed.
                if (robot.getInfraRedSensor().get(i).isDisplayAsGraphic()) {
                    // Convert angle to radians
                    irRadians = toRadians(irAngles[i]);
                    //Get x and y coordinates of the IR line start position

                    irStartX = posX + (int) (irLineStart * sin(irRadians));
                    irStartY = posY + (int) (irCosCoeffs[i] * irLineStart * cos(irRadians));

                    // Calculate the length of the IR line.
                    irLineLengthRatio = (float) robot.getInfraRedSensor().get(i).getVal() / (float) irMaxValue;
                    irLineLength = (int) (irLineLengthRatio * irLineMaxLength);

                    //Get x and y coordinates of the line end position
                    irEndX = irStartX + (int) (irLineLength * sin(irRadians));
                    irEndY = irStartY + (int) (irCosCoeffs[i] * irLineLength * cos(irRadians));

                    //Draw IR Line
                    canvas.drawLine(irStartX, irStartY, irEndX, irEndY, irPaint);
                }

            }
        }

    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}
