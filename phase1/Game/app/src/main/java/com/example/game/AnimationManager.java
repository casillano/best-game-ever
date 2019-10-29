package com.example.game;

import android.graphics.Canvas;
import android.graphics.Rect;

class AnimationManager {
    private Animation[] animations;
    private int animationIndex = 0;

    AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    void playAnimation(int index) {
        for (int i = 0; i < animations.length; i++) {
            if (i == index) {
                if (!animations[index].isPlaying())
                    animations[i].play();
            } else {
                animations[i].stop();
            }
            animationIndex = index;
        }
    }

    void draw(Canvas canvas, Rect rect) {
        if (animations[animationIndex].isPlaying()) {
            animations[animationIndex].draw(canvas, rect);
        }
    }

    void update() {
        if (animations[animationIndex].isPlaying()) {
            animations[animationIndex].update();
        }
    }

}
