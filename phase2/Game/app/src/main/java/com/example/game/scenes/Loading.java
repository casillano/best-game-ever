package com.example.game.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import com.example.game.R;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

import com.example.game.design.Background;
import com.example.game.design.Button;

import java.util.concurrent.TimeUnit;

public class Loading implements Scene{
    private Background background;
    private SceneManager manager;
    private int counter;

    public Loading(SceneManager manager, Background background){
        this.manager = manager;
        this.background = background;
        this.counter = 0;
    }

    public void update() {
        background.update();
        counter++;
        System.out.println(counter);
        if(counter > 30) {
            counter = 0;
            SceneManager.ACTIVE_SCENE = SceneManager.nextScene;
        }
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(150);
        canvas.drawText("LOADING...", 100, 1000, paint);
    }

    public void terminate() {
        SceneManager.ACTIVE_SCENE = SceneManager.nextScene;
    }

    public void receiveTouch(MotionEvent event) {
    }




}
