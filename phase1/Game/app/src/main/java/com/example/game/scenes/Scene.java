package com.example.game.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    void update();

    void draw(Canvas canvas);

    void terminate();

    void receiveTouch(MotionEvent event);

}
