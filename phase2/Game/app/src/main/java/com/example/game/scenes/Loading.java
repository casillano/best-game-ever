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
//    int nextScene;
    private Button play;

    public Loading(SceneManager manager, Background background){
        this.manager = manager;
        this.background = background;
//        this.nextScene = nextScene;
        this.play = new Button(100, 1200, 580, 100, "Play");
    }

    public void update() {
        background.update();
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(150);
        canvas.drawText("LOADING...", 100, 1000, paint);
//        try
//        {
//            TimeUnit.SECONDS.sleep(10);
//        }
//        catch(InterruptedException e){}

        play.draw(canvas);
    }

    public void terminate() {
        SceneManager.ACTIVE_SCENE = SceneManager.nextScene;
    }

    public void receiveTouch(MotionEvent event) {
        if (play.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = SceneManager.nextScene;
//            manager.resetScenes();
        }

    }




}
