package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.lang.Math;

public class SlimeMonster implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    private int speed;
    private int max_health = 100;
    private HealthBar healthBar;
    private int damage;

    SlimeMonster() {
        speed = 5;
        damage = 1;
        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.slime);
        this.rectangle = new Rect(Constants.DISPLAY_SIZE.x/2 - 50, 50,
                Constants.DISPLAY_SIZE.x/2 + 50, 150);
        Bitmap walk1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
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
        animationManager = new AnimationManager((new Animation[]{idle, walkRight, walkleft}));
        Point p = new Point();
        p.x = this.rectangle.centerX();
        p.y = this.rectangle.centerY() - (idleImg.getWidth()/2);
        healthBar = new HealthBar(max_health, p, Color.RED, 100);
    }

    @Override
    public void draw(Canvas canvas) {
        // canvas.drawRect(rectangle, new Paint()); // THIS HAS TO BE DELETED BUT SHOWS THE PLAYER RECT
        animationManager.draw(canvas, rectangle);
        healthBar.draw(canvas);

    }

    @Override
    public void update() {
        animationManager.update();

    }

    void update(Player player) {
        double oldLeft = rectangle.left;
        float[] normal = new float[2];
        normal[0] = player.getRectangle().centerX() - rectangle.centerX();
        normal[1] = player.getRectangle().centerY() - rectangle.centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        if (magnitude <= (float)(rectangle.width()/2 + player.getRectangle().width()/2)){
            player.healthBar.take_damage(this.damage); //NEEDS SOME REVISION!!!!
        }
        if (magnitude < speed) {
            animationManager.playAnimation(0);
            return;
        }
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int) (un[0] * speed);
        int move_y = (int) (un[1] * speed);

        rectangle.set((rectangle.centerX() + move_x) - rectangle.width() / 2,
                (rectangle.centerY() + move_y) - rectangle.height() / 2,
                (rectangle.centerX() + move_x) + rectangle.width() / 2,
                (rectangle.centerY() + move_y) + rectangle.height() / 2);
        healthBar.move(move_x, move_y);

        int state = 0; // 0 idle, 1 walking , 2 walking left
        if (rectangle.left - oldLeft > 0) {
            state = 1;
        } else if (rectangle.left - oldLeft < 0) {
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();

    }
}
