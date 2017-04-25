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
    int realWidth;
    int realHeight;
    int pixelWidth;
    int pixelHeight;
    float heightRatio;
    Rect rect;
    Paint paint;

    public ArenaBorder(Context context, int x, int y) {
        super(context);
        realWidth = x;
        realHeight = y;

        heightRatio = ((float)realHeight/(float)realWidth);

        //Width in pixels is the length of the arena fragment
        pixelWidth = ArenaFragment.arenaFragWidth - (ArenaFragment.arenaMargin);
        pixelHeight = (int)(pixelWidth * heightRatio);

        //If height is too large for the screen, scale the border down until it fits.
        while(pixelHeight>(ArenaFragment.arenaFragHeight -(ArenaFragment.arenaMargin))) {
            pixelHeight--;
            pixelWidth--;
        }

        rect = new Rect(ArenaFragment.arenaMargin,ArenaFragment.arenaMargin,pixelWidth,pixelHeight);

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

    public int getRealWidth() {
        return realWidth;
    }

    public int getRealHeight() {
        return realHeight;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

    public Rect getRect() {
        return rect;
    }
}
