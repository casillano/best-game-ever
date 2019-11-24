package com.example.game.actors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private Paint paint;
    private Paint backPaint;
    private String name;

    public Button(int xCo, int yCo, int w, int h, String n) {
        x = xCo;
        y = yCo;
        width = w;
        height = h;
        name = n;
        paint = new Paint();
        backPaint = new Paint();
        backPaint.setColor(Color.rgb(0, 0, 0));
        paint.setColor(Color.rgb(0, 100, 0));
    }

    public void draw(Canvas canvas) {
        paint.setStrokeWidth(3);
        backPaint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(x - 6, y - 6, x + width + 6, y + height + 6, backPaint);
        canvas.drawRect(x, y, x + width, y + height, paint);
        backPaint.setTextSize(100);
        canvas.drawText(name, x + (width - 50 * name.length())/2, y + (int) ((height + 80) / 2), backPaint);
    }

    public boolean isClicked(int xCo, int yCo) {
        backPaint.setColor(Color.rgb(255, 255, 255));
        return (x < xCo && x + width > xCo && y < yCo && y + height > yCo);
    }

    public String getName() {
        return name;
    }
}
