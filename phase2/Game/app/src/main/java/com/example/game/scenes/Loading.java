package com.example.game.scenes;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import com.example.game.R;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

import com.example.game.design.Background;

public class Loading implements Scene{
    private Background background;
    private SceneManager manager;
    private int nextScene;
    private int counter;

    public Loading(SceneManager manager, Background background, int nextScene){
        this.manager = manager;
        this.background = background;
        this.nextScene = nextScene;
    }

    public void update() {
        background.update();
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
    }

    public void terminate() {
        SceneManager.ACTIVE_SCENE = nextScene;
    }

    public void receiveTouch(MotionEvent event) {}




}
