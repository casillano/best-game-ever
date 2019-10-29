package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;

public class GameplayScene implements Scene {
    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();
    private int score = 0;
    private SceneManager manager;
    private int xp;

    GameplayScene(Context context, SceneManager manager) {
        player = new Player(context);
        this.manager = manager;
        xp = manager.getXp();
        monsters.add(new SlimeMeleeMonster(context, 100, 100));
        monsters.add(new SlimeMeleeMonster(context, 1000, 1000));
        background = new Background(context);
        playerPoint = new Point(Constants.DISPLAY_SIZE.x / 2, Constants.DISPLAY_SIZE.y);
        quitButton = new Button(850, 50, 100, 100, "X");
    }

    @Override
    public void update() {
        score++;
        if (score > 200) {
            if(player.getHealth() > 5) xp = score + player.getHealth();
            else xp = 0;
            SceneManager.ACTIVE_SCENE = 0;
            manager.resetScenes();
        }
        background.update();
        player.update(playerPoint);
        for (SlimeMeleeMonster m : monsters) {
            m.update(player);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        player.draw(canvas);
        for (SlimeMeleeMonster m : monsters) {
            m.draw(canvas);
        }
        quitButton.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("SCORE: " + score, 30, 100, paint);
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
            manager.resetScenes();
        }
    }

    int getXp(){
        return xp;
    }
}
