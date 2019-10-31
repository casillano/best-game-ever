package com.example.game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Random;

public class GlassScene implements Scene{

    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private int numMonsters;
    private int score = 0;
    private Button[] buttons ;
    private Button enter;
    private SceneManager manager;
    private String userInput;
    private int xp;
    private ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();

    GlassScene(Context context, SceneManager manager) {
        player = new Player(context);
        this.manager = manager;
        xp = manager.getXp();
        Random rand = new Random();
        numMonsters = rand.nextInt(20);
        userInput = "";
//      Generating a random number of monsters
        for (int i = 0; i <= numMonsters; i++) {
            monsters.add(new SlimeMeleeMonster(context, rand.nextInt(1000), rand.nextInt(1000)));
            monsters.add(new SlimeMeleeMonster(context, rand.nextInt(1000), rand.nextInt(1000)));
        }
        enter = new Button(500, 1700, 200, 200, "Enter");
        buttons = new Button[10];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(100*i + 37, 1500, 100, 100, i+"");
        }
        background = new Background(context);
        playerPoint = new Point(Constants.DISPLAY_SIZE.x / 2, Constants.DISPLAY_SIZE.y);
        quitButton = new Button(850, 50, 100, 100, "X");
        for (SlimeMeleeMonster m : monsters) {
            m.update(player);
        }
    }

    @Override
    public void update() {

        background.update();
        player.update(playerPoint);
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        for (int i = 0; i < 10; i++) {
            buttons[i].draw(canvas);
        }
        enter.draw(canvas);
        for (SlimeMeleeMonster m : monsters) {
            m.draw(canvas);
        }
        quitButton.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("SCORE: " + score, 30, 100, paint);
        canvas.drawText("input: " + userInput, 30, 1200, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 0;
            xp = 0;
            manager.resetScenes();
        }
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isClicked((int) event.getX(), (int) event.getY())) {
                userInput += buttons[i].getName();
            }
        }
    }






}
