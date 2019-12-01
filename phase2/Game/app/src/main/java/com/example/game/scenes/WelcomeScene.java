package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.game.design.Background;
import com.example.game.design.Button;

public class WelcomeScene implements Scene {
    private Background background;
    private Button loginButton, signButton;
    private SceneManager manager;
    private int xp;

    WelcomeScene(Context context, SceneManager manager, Background background) {
        this.manager = manager;
        this.background = background;
        signButton = new Button(100, 1200, 880, 150, "New User");
        loginButton = new Button(100, 1400, 880, 150, "Login");

    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        loginButton.draw(canvas);
        signButton.draw(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {

        if (loginButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 0;
            SceneManager.ACTIVE_SCENE = 9;
            xp = 0;
            manager.resetScenes();

        }
        if (signButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 7;
            SceneManager.ACTIVE_SCENE = 9;
            xp = 0;
            manager.resetScenes();
        }
    }
}
