package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint; //only needed to draw RECTs in draw()
import android.graphics.Rect;


public class Character implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    int speed;
    int max_health = 100;
    HealthBar healthBar;
    int damage;

    @Override
    public void draw(Canvas canvas) {
        // canvas.drawRect(rectangle, new Paint()); // SHOWS ALL CHARACTER RECTs
        animationManager.draw(canvas, rectangle);
        healthBar.draw(canvas);
    }

    @Override
    public void update() {
        animationManager.update();
    }

    Rect getRectangle() { //Not sure why it is marked as not used. This is used.
        return rectangle;
    }

    void setRectangle(Rect rect) {
        this.rectangle = rect;
    }

    void setAnimationManager(AnimationManager manager) {
        this.animationManager = manager;
    }

    AnimationManager getAnimationManager() {
        return animationManager;
    }

    void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    void changeRectangle(int left, int top, int right, int bottom) {
        rectangle.set(left, top, right, bottom);

    }
}
