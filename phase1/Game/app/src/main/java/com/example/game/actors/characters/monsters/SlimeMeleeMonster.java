package com.example.game.actors.characters.monsters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.game.actors.characters.HealthBar;
import com.example.game.actors.characters.monsters.MeleeMonster;
import com.example.game.R;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

public class SlimeMeleeMonster extends MeleeMonster {
    public SlimeMeleeMonster(Context context, int x, int y) {
        speed = 15;
        damage = 5;
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slime);
        setRectangle(new Rect(x - 50, y - 50,
                x + 50, y + 50));
        Bitmap walk1 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slime_walk);

        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkleft = new Animation(new Bitmap[]{idleImg, walk1}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), m,
                false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        Animation walkRight = new Animation(new Bitmap[]{idleImg, walk1}, 0.5f);

        // All animations in Player
        setAnimationManager(new AnimationManager((new Animation[]{idle, walkRight, walkleft})));
        Point p = new Point();
        p.x = getRectangle().centerX();
        p.y = getRectangle().centerY() - (idleImg.getWidth() / 2);
        healthBar = new HealthBar(max_health, p, Color.RED, 100);
    }
}
