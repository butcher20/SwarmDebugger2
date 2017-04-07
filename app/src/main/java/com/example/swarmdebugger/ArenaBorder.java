package com.example.swarmdebugger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Alex on 04/04/2017.
 */

public class ArenaBorder extends View {
    int width;
    int height;
    float heightRatio;
    Rect rect;
    Paint paint;

    public ArenaBorder(Context context, int x, int y) {
        super(context);
        width = x;
        height = y;

        heightRatio = ((float)height/(float)width);

        //Actual width is the length of the arena fragment
        width = ArenaFragment.arenaFragWidth - (ArenaFragment.arenaMargin);
        height = (int)(width * heightRatio);

        //If height is too large for the screen, scale the border down until it fits.
        while(height>(ArenaFragment.arenaFragHeight -(ArenaFragment.arenaMargin))) {
            height = height-1;
            width = width-1;
        }

        rect = new Rect(new Rect(ArenaFragment.arenaMargin,ArenaFragment.arenaMargin,width,height));

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect, paint);

    }
}
