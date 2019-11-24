package com.example.game.design;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.game.backend.Constants;
import com.example.game.actors.GameObject;
import com.example.game.R;


public class Background implements GameObject {
    private Rect rectangle;
    private Animation idle;
    private AnimationManager animationManager;

    public Background(Context context) {
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.grass);
        this.rectangle = new Rect(0, 0, Constants.DISPLAY_SIZE.x, Constants.DISPLAY_SIZE.y);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animationManager = new AnimationManager((new Animation[]{idle}));

    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        idle.play();
        animationManager.update();

    }

}
