package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.game.backend.characters.monsters.BeeStrafingMonster;
import com.example.game.design.Background;
import com.example.game.design.Button;
import com.example.game.backend.Constants;
import com.example.game.backend.characters.player.Player;
import com.example.game.backend.characters.monsters.SlimeMeleeMonster;
import com.example.game.backend.characters.Character;


import java.util.ArrayList;

public class SurvivalScene implements Scene {
    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private ArrayList<Character> characters = new ArrayList<>();
    private int score = 0;
    private SceneManager manager;
    private int xp;
    private Context context;
    private ArrayList<Character> vanishingCharacters = new ArrayList<>();

    SurvivalScene(Context context, SceneManager manager, Background background) {
        this.context = context;
        player = new Player(context, SceneManager.getCostume());
        player.setCostume(SceneManager.getCostume());
        this.manager = manager;
        xp = 0;
        createMonsters();
        this.background = background;
        playerPoint = new Point(Constants.DISPLAY_SIZE.x / 2, Constants.DISPLAY_SIZE.y);
        quitButton = new Button(850, 50, 100, 100, "X");
    }

    //Updates the scene.
    @Override
    public void update() {
        score++;
        if (player.getHealth() < 1) {
            xp = score;
            SceneManager.nextScene = 1;
            SceneManager.ACTIVE_SCENE = 9;
            player.resetHealth();
            manager.resetScenes();
        }
        background.update();
        player.update(playerPoint);
        ArrayList<SlimeMeleeMonster> slimeMonsters = new ArrayList<>();
        handleMonsterDeaths();
        for (Character m : characters) {
            if (m instanceof SlimeMeleeMonster)
                slimeMonsters.add((SlimeMeleeMonster) m);
        }

        for (SlimeMeleeMonster m : slimeMonsters) {
            ArrayList<SlimeMeleeMonster> collidableCharacter = new ArrayList<>(slimeMonsters);
            collidableCharacter.remove(m);
            m.update(player, collidableCharacter);
        }
        for (Character m : characters) {
            if (m instanceof BeeStrafingMonster) {
                ((BeeStrafingMonster) m).update(player, slimeMonsters);
            }
        }

    }

    //Draws the scene.
    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        player.draw(canvas);
        for (Character m : characters) {
            m.draw(canvas);
        }
        ArrayList<Character> toRemove = new ArrayList<>();
        for (Character m : vanishingCharacters) {
            if (m.counter > 0) {
                m.draw(canvas);
                m.counter -= 1;
            } else {
                toRemove.add(m);
            }
        }
        for (Character m : toRemove) {
            vanishingCharacters.remove(m);
        }
        toRemove.clear();
        quitButton.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("SCORE: " + score, 30, 100, paint);
    }

    //Terminates a scene.
    @Override
    public void terminate() {
        SceneManager.nextScene = 1;
        SceneManager.ACTIVE_SCENE = 9;
    }

    //Handles touch events.
    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
        }
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 1;
            SceneManager.ACTIVE_SCENE = 9;
            xp = 0;
            manager.resetScenes();
        }
    }

    //Returns the XP collected.
    int getXp() {
        return xp;
    }

    //Creates the monsters in the scene.
    private void createMonsters() {
        characters.add(new SlimeMeleeMonster(context, 100, 100));
        characters.add(new SlimeMeleeMonster(context, 400, 600));
        characters.add(new SlimeMeleeMonster(context, 100, 1000));
        characters.add(new SlimeMeleeMonster(context, 400, 80));
        characters.add(new BeeStrafingMonster(context, -100, 500));
        characters.add(new BeeStrafingMonster(context, Constants.DISPLAY_SIZE.x + 100, 1000));
    }

    void setCostume(String color) {
        player.setCostume(color);
    }

    // Removes and creates new monsters upon death
    private void handleMonsterDeaths() {
        ArrayList<Character> toRemove = new ArrayList<>();
        for (Character m : characters) {
            if (m.healthBar.getCurrHealth() == 0) {
                toRemove.add(m);
            }
        }
        for (Character m : toRemove) {
            m.getAnimationManager().playAnimation(m.deathDirection);
            vanishingCharacters.add(m);
            characters.remove(m);
            characters.add(new SlimeMeleeMonster(context,
                    (int) (Math.random() * Constants.DISPLAY_SIZE.x),
                    Constants.DISPLAY_SIZE.y - 100));
        }
        toRemove.clear();
    }
}
