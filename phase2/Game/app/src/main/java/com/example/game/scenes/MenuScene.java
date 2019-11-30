package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


import com.example.game.design.Background;
import com.example.game.design.Button;



public class MenuScene implements Scene {
    private Background background;
    private Button gameButton, game2Button, game3Button, storeButton, changeUser, scoreButton;
    private SceneManager manager;
    private int xp;
    private int xp1;
    private int xp2;
    private int xp3;

    MenuScene(Context context, SceneManager manager) {
        this.manager = manager;
        background = new Background(context);
        gameButton = new Button(100, 800, 880, 150, "GAME 1");
        game2Button = new Button(100, 1000, 880, 150, "GAME 2");
        game3Button = new Button(100, 1200, 880, 150, "GAME 3");
        storeButton = new Button(100, 1400, 880, 150, "Customization");
        scoreButton = new Button(100, 1600, 880, 150, "Scoreboard");
        changeUser = new Button(500, 1800, 480, 150, "CHANGE USER");
        xp = manager.getXp();
        xp1 = manager.getXp1();
        xp2 = manager.getXp2();
        xp3 = manager.getXp3();
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
        scoreButton.draw(canvas);
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
        paint.setTextSize(50);
        canvas.drawText("Game1 XP: " + xp1, 500, 80, paint);
        canvas.drawText("Game2 XP: " + xp2, 500, 120, paint);
        canvas.drawText("Game3 XP: " + xp3, 500, 160, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
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
        else if (scoreButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 8;
        }
    }

    void setXp(int points){
        xp = points;
    }
}