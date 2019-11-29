package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import com.example.game.design.Button;
import com.example.game.design.StoreBackground;

public class WelcomeScene implements Scene {
    private StoreBackground background;
    private Button loginButton, signButton;
    private SceneManager manager;
    private int xp;

    WelcomeScene(Context context, SceneManager manager) {
        this.manager = manager;
        background = new StoreBackground(context);
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
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();

        }
        if (signButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 7;
            xp = 0;
            manager.resetScenes();
        }
    }
}
