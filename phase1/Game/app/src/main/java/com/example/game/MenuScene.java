package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;

public class MenuScene implements Scene {
    private Background background;
    private Button gameButton, game2Button, game3Button, storeButton, changeUser;
    private SceneManager manager;
    private int xp;
    private boolean blocked = true;
    private Handler handler = new Handler();

    MenuScene(Context context, SceneManager manager) {
        this.manager = manager;
        background = new Background(context);
        gameButton = new Button(100, 1000, 880, 150, "GAME 1");
        game2Button = new Button(100, 1200, 880, 150, "GAME 2");
        game3Button = new Button(100, 1400, 880, 150, "GAME 3");
        storeButton = new Button(100, 1600, 880, 150, " Store");
        changeUser = new Button(100, 1800, 880, 150, "CHANGE USER");
        xp = manager.getXp();
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        gameButton.draw(canvas);
        game2Button.draw(canvas);
        game3Button.draw(canvas);
        storeButton.draw(canvas);
        changeUser.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("XP: " + xp, 30, 100, paint);
        paint.setTextSize(70);
        canvas.drawText("USER: " + manager.getUserName(), 30, 170, paint);
        paint.setTextSize(200);
        canvas.drawText("BEST", 300, 400, paint);
        canvas.drawText("GAME", 250, 550, paint);
        canvas.drawText("EVER!", 280, 700, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (blocked)
            {
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        blocked = false;
                    }
                }, 1000);}
            else {
                if (gameButton.isClicked((int) event.getX(), (int) event.getY())) {
                    SceneManager.ACTIVE_SCENE = 2;
                }
                else if (game2Button.isClicked((int) event.getX(), (int) event.getY())) {
                    SceneManager.ACTIVE_SCENE = 3;
                }
                else if (game3Button.isClicked((int) event.getX(), (int) event.getY())) {
                    SceneManager.ACTIVE_SCENE = 4;
                }
                else if (storeButton.isClicked((int) event.getX(), (int) event.getY())) {
                    SceneManager.ACTIVE_SCENE = 5;
                }
                else if (changeUser.isClicked((int) event.getX(), (int) event.getY())) {
                    SceneManager.ACTIVE_SCENE = 0;
                    manager.changeUser();
                }
            }

        }
    }

    void setXp(int points){
        xp = points;
    }
}

