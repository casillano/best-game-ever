package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MenuPanel extends Panel{
    private MainThread thread;
    private Background background;
    private Button gameButton;
    private MainActivity myContext;

    public MenuPanel(Context context) {
        super(context);
        myContext = (MainActivity) context;
        getHolder().addCallback(this);
        Display display = ((MainActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Constants.DISPLAY_SIZE = size;
        thread = new MainThread(getHolder(), this);
        background = new Background(context);
        gameButton = new Button(100,1000,880,150, "GAME 1");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gameButton.isClicked((int) event.getX(), (int) event.getY())){
            myContext.setContentView(new GamePanel(myContext));
        }
        return true;
    }

    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        background.draw(canvas);
        gameButton.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
