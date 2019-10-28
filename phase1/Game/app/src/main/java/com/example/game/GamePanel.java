package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends Panel{
    private MainThread thread;
    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private MainActivity myContext;
    private ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();

    public GamePanel(Context context) {
        super(context);
        myContext = (MainActivity) context;
        getHolder().addCallback(this);
        Display display = ((MainActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Constants.DISPLAY_SIZE = size;

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        player = new Player(context);
        monsters.add(new SlimeMeleeMonster(context, 100, 100));
        monsters.add(new SlimeMeleeMonster(context, 1000, 1000));
        background = new Background(context);
        playerPoint = new Point(size.x / 2, size.y);
        quitButton = new Button(850, 50, 100, 100, "X");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        try {
            thread.setRunning(false);
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
        }
        if(quitButton.isClicked((int) event.getX(), (int) event.getY())){
            myContext.setContentView(new MenuPanel(myContext));
        }
        return true;
    }

    public void update() {
        background.update();
        player.update(playerPoint);
        for (SlimeMeleeMonster m : monsters) {
            m.update(player);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        background.draw(canvas);
        player.draw(canvas);
        for (SlimeMeleeMonster m : monsters) {
            m.draw(canvas);
        }
        quitButton.draw(canvas);
    }
}
