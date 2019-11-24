package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.game.design.Background;
import com.example.game.actors.Button;
import com.example.game.backend.Constants;
import com.example.game.actors.characters.player.Player;
import com.example.game.actors.characters.monsters.SlimeMeleeMonster;


import java.util.ArrayList;

public class GameOneScene implements Scene {
    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();
    private int score = 0;
    private SceneManager manager;
    private int xp;

    GameOneScene(Context context, SceneManager manager) {
        player = new Player(context, Constants.playerColor);
        this.manager = manager;
        xp = 0;
        monsters.add(new SlimeMeleeMonster(context, 100, 100));
        monsters.add(new SlimeMeleeMonster(context, 400, 600));
        monsters.add(new SlimeMeleeMonster(context, 100, 1000));
        monsters.add(new SlimeMeleeMonster(context, 40, 600));
        monsters.add(new SlimeMeleeMonster(context, 1030, 100));
        monsters.add(new SlimeMeleeMonster(context, 420, 80));
        background = new Background(context);
        playerPoint = new Point(Constants.DISPLAY_SIZE.x / 2, Constants.DISPLAY_SIZE.y);
        quitButton = new Button(850, 50, 100, 100, "X");
    }

    @Override
    public void update() {
        score++;
        if (player.getHealth() < 1) {
            xp = score;
            SceneManager.ACTIVE_SCENE = 1;
            manager.resetScenes();
        }
        background.update();
        player.update(playerPoint);
        ArrayList<SlimeMeleeMonster> slimeMonsters = new ArrayList<>();
        for (SlimeMeleeMonster m : monsters) {
            if (m.healthBar.getCurrHealth() == 0) {
                monsters.remove(m);
            } else {
                slimeMonsters.add(m);
            }

        }
        for (SlimeMeleeMonster m : monsters) {
            ArrayList<SlimeMeleeMonster> collidableCharacter = new ArrayList<>(slimeMonsters);
            collidableCharacter.remove(m);
            m.update(player, collidableCharacter);
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
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
        }
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 1;
            xp = 0;
            manager.resetScenes();
        }
    }

    int getXp() {
        return xp;
    }
}
