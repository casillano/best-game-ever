package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MazeScene implements Scene {

    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private int score = 0;
    private SceneManager manager;
    private MazeCreator mazeCreator;
    private int xp;

    MazeScene(Context context, SceneManager manager) {
        this.manager = manager;
        xp = manager.getXp();

        background = new Background(context);
        playerPoint = new Point(Constants.DISPLAY_SIZE.x / 2, Constants.DISPLAY_SIZE.y);
        quitButton = new Button(850, 50, 100, 100, "X");
        mazeCreator = new MazeCreator();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        quitButton.draw(canvas);
        mazeCreator.drawMaze(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
        }
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();
        }
    }
}
