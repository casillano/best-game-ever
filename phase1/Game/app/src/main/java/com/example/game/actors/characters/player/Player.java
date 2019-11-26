package com.example.game.actors.characters.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


import com.example.game.backend.Constants;
import com.example.game.actors.characters.HealthBar;
import com.example.game.R;
import com.example.game.actors.characters.Character;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

import java.lang.Math;

public class Player extends Character {
    private Bitmap idleImg, walk1,walk2;
    private String col;
    public Player(Context context, String col) {
        speed = 15;
        max_health = 250;
        this.col = col;
        setAnimations(context);

        setRectangle(new Rect(Constants.DISPLAY_SIZE.x / 2 - 50,
                Constants.DISPLAY_SIZE.y - 50, Constants.DISPLAY_SIZE.x / 2 + 50,
                Constants.DISPLAY_SIZE.y + 50));
        int aboveDistance = getRectangle().width()/2;
        setHealthBar(new HealthBar(max_health, this, aboveDistance, Color.GREEN, 150));
    }

    public void update(Point point) {
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
        healthBar.move();

        int state = 0; // 0 blueidle, 1 walking , 2 walking left
        if (getRectangle().left - oldLeft > 0) {
            state = 1;
        } else if (getRectangle().left - oldLeft < 0) {
            state = 2;
        }

        getAnimationManager().playAnimation(state);
        getAnimationManager().update();

    }

    public int getHealth(){
        return healthBar.getCurrHealth();
    }

    public void setAnimations(Context context) {
        if (col.equals("blue")) {
            idleImg = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.blueidle);
            walk1 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.bluewalk1);
            walk2 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.bluewalk2);
        }

        else if (col.equals("green")) {
            idleImg = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.greenidle);
            walk1 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.greenwalk1);
            walk2 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.greenwalk2);
        }

        else if (col.equals("pink")) {
            idleImg = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.pinkidle);
            walk1 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.pinkwalk1);
            walk2 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.pinkwalk2);
        }

        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m,
                false);
        Animation walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        // All animations in Player
        setAnimationManager(new AnimationManager((new Animation[]{idle, walkRight, walkLeft})));
    }
}