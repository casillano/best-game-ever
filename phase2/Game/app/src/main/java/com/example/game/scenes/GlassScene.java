package com.example.game.scenes;

// importing the required packages

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.game.backend.Builder;
import com.example.game.backend.characters.monsters.BeeStrafingMonster;
import com.example.game.design.Background;
import com.example.game.design.Button;
import com.example.game.backend.Constants;
import com.example.game.backend.characters.player.Player;
import com.example.game.backend.characters.monsters.SlimeMeleeMonster;

import java.util.ArrayList;

// implementing the game3 class: a random number of monsters move-about/show-up on the screen and
// the player has to guess the number of monsters.
public class GlassScene implements Scene {

    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    static private int score;
    private Button[] buttons;
    private Button enter;
    private Button erase;
    private SceneManager manager;
    private String userInput;
    private int guess;
    private int xp;
    private int counter;
    private ArrayList<BeeStrafingMonster> monsters;

    GlassScene(Context context, SceneManager manager, Background background) {
        guess = 0;
        counter = 0;
        player = new Player(context, SceneManager.getCostume());
        this.manager = manager;
        this.background = background;
        xp = 0;
        userInput = "";

        //      Creating the required buttons
        enter = new Button(400, 1700, 300, 100, "Enter");
        erase = new Button(400, 1900, 300, 100, "Erase");
        quitButton = new Button(850, 50, 100, 100, "X");

        //      creating the input keyboard using a Builder method
        this.buttons = Builder.buildNumericKB();

        // maybe not needed ?
        playerPoint = new Point(Constants.displaySize.x / 2, Constants.displaySize.y);

        //      Generating a random number of monsters using Builder
        monsters = Builder.buildBee(context);
        //      Creating an empty list of slime monsters because it is required to update the Bee
        // monsters'
        //      movement.
        ArrayList<SlimeMeleeMonster> emptyList = new ArrayList<>();

        //      Update each bee's movement
        for (BeeStrafingMonster m : monsters) {
            m.update(player, emptyList);
        }
    }

    @Override
    // update the background the player input
    public void update() {
        background.update();
        player.update(playerPoint);
    }

    @Override
    // draw the various entities on the 'Canvas'
    public void draw(Canvas canvas) {
        background.draw(canvas);
        for (int i = 0; i < 10; i++) {
            buttons[i].draw(canvas);
        }
        enter.draw(canvas);
        for (BeeStrafingMonster m : monsters) {
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
        SceneManager.activeScene = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 1;
            SceneManager.activeScene = 9;
            xp = 0;
            manager.resetScenes();
        }
        for (Button button : buttons) {
            if (button.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    userInput += button.getName();
                }
                counter += 1;
            }
        }
        if (enter.isClicked((int) event.getX(), (int) event.getY())) {
            try {
                guess = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            if (guess == monsters.size()) {
                xp = 200;
                score += guess;
            } else {
                xp = 0;
            }

            SceneManager.nextScene = 4;
            SceneManager.activeScene = 9;
            //            this.terminate();
            manager.resetScenes();
        }
        if (erase.isClicked((int) event.getX(), (int) event.getY())) {
            userInput = "";
        }
    }

    void setCostume(String color) {
        player.setCostume(color);
    }

    int getXp() {
        return xp;
    }
}
