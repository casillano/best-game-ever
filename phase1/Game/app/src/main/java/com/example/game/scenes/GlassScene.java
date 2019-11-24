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
import java.util.Random;

import static java.lang.Thread.sleep;

public class GlassScene implements Scene {

    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private int numMonsters;
    private int score = 0;
    private Button[] buttons ;
    private Button enter;
    private Button erase;
    private SceneManager manager;
    private String userInput;
    private int guess;
    private int xp;
    private int counter;
    private ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();

    GlassScene(Context context, SceneManager manager) {
        guess = 0;
        counter = 0;
        player = new Player(context, Constants.playerColor);
        this.manager = manager;
        xp = 0;
        Random rand = new Random();
        numMonsters = rand.nextInt(20);
        userInput = "";
//      Generating a random number of monsters
        for (int i = 0; i <= numMonsters; i++) {
            monsters.add(new SlimeMeleeMonster(context, rand.nextInt(1000), rand.nextInt(1000)));
            monsters.add(new SlimeMeleeMonster(context, rand.nextInt(1000), rand.nextInt(1000)));
        }
        enter = new Button(400, 1700, 300, 100, "Enter");
        erase = new Button(400, 1900, 300, 100, "Erase");
        buttons = new Button[10];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(100*i + 37, 1500, 100, 100, i+"");
        }
        background = new Background(context);
        playerPoint = new Point(Constants.DISPLAY_SIZE.x / 2, Constants.DISPLAY_SIZE.y);
        quitButton = new Button(850, 50, 100, 100, "X");
        ArrayList<SlimeMeleeMonster> emptyList = new ArrayList<>();
        for (SlimeMeleeMonster m : monsters) {
            m.update(player, emptyList);
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
        erase.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("SCORE: " + score, 30, 100, paint);
        canvas.drawText("input: " + userInput, 30, 1200, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 1;
            xp = 0;
            manager.resetScenes();
        }
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isClicked((int) event.getX(), (int) event.getY())) {
                if(counter % 2 == 0) {
                    userInput += buttons[i].getName();
                }
                counter += 1;
            }
        }
        if (enter.isClicked((int) event.getX(), (int) event.getY())) {
            try {
                guess = Integer.parseInt(userInput);
            }catch (NumberFormatException e){
            }
            if (guess == monsters.size()) {
               xp = 200;
           }
           else {
               xp = 0;
           }
//            System.out.println("guess" + guess + "num" + monsters.size());
            SceneManager.ACTIVE_SCENE = 1;
            manager.resetScenes();
        }
        if (erase.isClicked((int) event.getX(), (int) event.getY())) {
            userInput = "";
        }
    }

    int getXp(){
        return xp;
    }






}
