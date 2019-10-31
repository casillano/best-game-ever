package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;

public class CustomizationScene implements Scene {
    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton, custom1Btn, custom2Btn, custom3Btn;
    private ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();
    private int score = 0;
    private SceneManager manager;
    private int xp;

    CustomizationScene(Context context, SceneManager manager) {
        this.manager = manager;
        xp = manager.getXp();
        background = new Background(context);
        quitButton = new Button(850, 50, 100, 100, "X");
        custom1Btn = new Button(150, 800, 300, 100, "green");
        custom2Btn = new Button(700, 800, 300, 100, "blue");
        custom3Btn = new Button(500, 1500, 100, 100, "pink");

    }

    @Override
    public void update() {

        background.update();

    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        quitButton.draw(canvas);
        custom1Btn.draw(canvas);
        custom2Btn.draw(canvas);
        custom3Btn.draw(canvas);
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

        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();

        }
        else if (custom1Btn.isClicked((int) event.getX(), (int) event.getY())) {
            Constants.playerColor = "green";
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();

        }
        else if (custom2Btn.isClicked((int) event.getX(), (int) event.getY())) {
            Constants.playerColor = "blue";
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();

        }
        else if (custom3Btn.isClicked((int) event.getX(), (int) event.getY())) {
            Constants.playerColor = "pink";
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();

        }
    }
}
