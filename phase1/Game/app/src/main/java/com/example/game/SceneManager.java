package com.example.game;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.content.SharedPreferences;

import java.util.ArrayList;

class SceneManager {
    private ArrayList<Scene> scenes;
    static int ACTIVE_SCENE;
    private Context context;
    private SharedPreferences pref;
    private Editor editor;
    private GameplayScene game1;
    private MenuScene menu;
    private MazeScene maze;
    private GlassScene game3;
    private CustomizationScene store;
    private int xp;

    SceneManager(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        ACTIVE_SCENE = 0;
        scenes = new ArrayList<>();
        addAllScenes();
        editor = pref.edit();
        xp = pref.getInt("xp", 0);
    }

    void receiveTouch(MotionEvent event) {
        if (scenes.size() > 0) {
            scenes.get(ACTIVE_SCENE).receiveTouch(event);
        }
    }

    void update() {
        if (scenes.size() > 0) {
            scenes.get(ACTIVE_SCENE).update();
        }
    }

    void draw(Canvas canvas) {
        if (scenes.size() > 0) {
            scenes.get(ACTIVE_SCENE).draw(canvas);
        }

    }

    void resetScenes() {
        xp += game1.getXp();
        xp += game3.getXp();
        editor.putInt("xp", xp);
        editor.apply();
        scenes.clear();
        addAllScenes();
    }

    private void addAllScenes(){
        game1 = new GameplayScene(context, this);
        menu = new MenuScene(context, this);
        maze = new MazeScene(context, this);
        game3 = new GlassScene(context, this);
        store  = new CustomizationScene(context, this);
        scenes.add(menu);
        scenes.add(game1);
        scenes.add(maze);
        scenes.add(game3);
        scenes.add(store);
    }

    int getXp(){
        return pref.getInt("xp", 0);
    }
}
