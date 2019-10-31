package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class MenuScene implements Scene {
    private Background background;
    private Button gameButton, game2Button, game3Button;
    private SceneManager manager;
    private int xp;

    MenuScene(Context context, SceneManager manager) {
        this.manager = manager;
        background = new Background(context);
        gameButton = new Button(100, 1000, 880, 150, "GAME 1");
        game2Button = new Button(100, 1200, 880, 150, "GAME 2");
        game3Button = new Button(100, 1400, 880, 150, "GAME 3");
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
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("XP: " + xp, 30, 100, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (gameButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 1;
        }
        else if (game2Button.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 2;
        }
        else if (game3Button.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 3;
        }
    }
}
