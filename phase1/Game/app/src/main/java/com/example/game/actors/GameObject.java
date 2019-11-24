package com.example.game.actors;

import android.graphics.Canvas;

public interface GameObject {
    void draw(Canvas canvas);

    void update();
}
