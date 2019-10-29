package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


import java.lang.Math;

class Player extends Character {

    Player(Context context) {
        speed = 15;
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.idle);
        setRectangle(new Rect(Constants.DISPLAY_SIZE.x / 2 - 50,
                Constants.DISPLAY_SIZE.y - 50, Constants.DISPLAY_SIZE.x / 2 + 50,
                Constants.DISPLAY_SIZE.y + 50));
        Bitmap walk1 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.walkright1);
        Bitmap walk2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.walkright2);

        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m,
                false);
        Animation walkleft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        // All animations in Player
        setAnimationManager(new AnimationManager((new Animation[]{idle, walkRight, walkleft})));
        Point p = new Point();
        p.x = getRectangle().centerX();
        p.y = getRectangle().centerY() - (idleImg.getWidth() / 2);
        setHealthBar(new HealthBar(max_health, p, Color.GREEN, 150));
    }

    void update(Point point) {
        double oldLeft = getRectangle().left;
        float[] normal = new float[2];
        normal[0] = point.x - getRectangle().centerX();
        normal[1] = point.y - getRectangle().centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        if (magnitude < speed) {
            getAnimationManager().playAnimation(0);
            return;
        }
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int) (un[0] * speed);
        int move_y = (int) (un[1] * speed);
        changeRectangle((getRectangle().centerX() + move_x) - getRectangle().width() / 2,
                (getRectangle().centerY() + move_y) - getRectangle().height() / 2,
                (getRectangle().centerX() + move_x) + getRectangle().width() / 2,
                (getRectangle().centerY() + move_y) + getRectangle().height() / 2);
        healthBar.move(move_x, move_y);

        int state = 0; // 0 idle, 1 walking , 2 walking left
        if (getRectangle().left - oldLeft > 0) {
            state = 1;
        } else if (getRectangle().left - oldLeft < 0) {
            state = 2;
        }

        getAnimationManager().playAnimation(state);
        getAnimationManager().update();

    }

    int getHealth(){
        return healthBar.getCurrHealth();
    }
}
