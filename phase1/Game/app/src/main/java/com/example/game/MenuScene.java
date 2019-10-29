package com.example.game;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class MenuScene implements Scene {
    private Background background;
    private Button gameButton;
    private SceneManager manager;

    public MenuScene(Context context, SceneManager manager) {
        this.manager = manager;
        background = new Background(context);
        gameButton = new Button(100, 1000, 880, 150, "GAME 1");
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        gameButton.draw(canvas);
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
    }
}
