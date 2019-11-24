package com.example.game.actors.characters;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.game.actors.GameObject;
import com.example.game.design.AnimationManager;


public class Character implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    public int speed;
    public int max_health = 100;
    public HealthBar healthBar;
    public int damage;

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

    public Rect getRectangle() { //Not sure why it is marked as not used. This is used.
        return rectangle;
    }

    public void setRectangle(Rect rect) {
        this.rectangle = rect;
    }

    public void setAnimationManager(AnimationManager manager) {
        this.animationManager = manager;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public void changeRectangle(int left, int top, int right, int bottom) {
        rectangle.set(left, top, right, bottom);

    }
}
