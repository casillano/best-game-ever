package com.example.game.backend;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.content.Context;
import android.view.SurfaceView;

import com.example.game.design.Button;
import com.example.game.design.Background;
import com.example.game.scenes.MainActivity;
import com.example.game.scenes.SceneManager;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Background background;
    private Button gameButton;
    private MainActivity myContext;
    private SceneManager manager;

    public GamePanel(Context context) {
        super(context);
        Display display = ((MainActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Constants.DISPLAY_SIZE = size;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        manager = new SceneManager(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.receiveTouch(event);
        return true;
    }


    public void update() {
        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        manager.draw(canvas);
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
