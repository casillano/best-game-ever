package com.example.game;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

class SceneManager {
    private ArrayList<Scene> scenes;
    static int ACTIVE_SCENE;
    private Context context;

    SceneManager(Context context) {
        this.context = context;
        ACTIVE_SCENE = 0;
        scenes = new ArrayList<>();
        scenes.add(new MenuScene(context, this));
        scenes.add(new GameplayScene(context, this));
    }

    void receiveTouch(MotionEvent event) {
        scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

    void update() {
        scenes.get(ACTIVE_SCENE).update();
    }
    void draw(Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    void reset_scenes(){
        scenes.clear();
        scenes.add(new MenuScene(context, this));
        scenes.add(new GameplayScene(context, this));
    }

}
