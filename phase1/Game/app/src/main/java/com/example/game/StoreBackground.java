package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class StoreBackground implements GameObject {
    private Rect rectangle;
    private Animation idle;
    private AnimationManager animationManager;

    StoreBackground(Context context) {
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.storebackground);
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
