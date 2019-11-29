package com.example.game.backend.characters;

import android.graphics.Canvas;

public interface GameObject {
    void draw(Canvas canvas);

    void update();
}
