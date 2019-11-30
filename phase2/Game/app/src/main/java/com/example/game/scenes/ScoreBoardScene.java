package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import com.example.game.design.Button;
import com.example.game.design.Background;

public class ScoreBoardScene implements Scene {
    private Background background;
    private Button quitButton, game1, game2, game3;
    private SceneManager manager;
    private int gameName;
    private String[][] highscore;
    private int[][] highscoreScores;

    ScoreBoardScene(Context context, SceneManager manager, Background background) {
        this.manager = manager;
        highscoreScores = new int[3][3];
        highscore = new String[3][3];
        this.background = background;
        quitButton = new Button(850, 50, 100, 100, "X");
        gameName = 0;
        game1 = new Button(100, 500, 293, 150, "G 1");
        game2 = new Button(393, 500, 293, 150, "G 2");
        game3 = new Button(686, 500, 293, 150, "G 3");
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        quitButton.draw(canvas);
        game1.draw(canvas);
        game2.draw(canvas);
        game3.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("GAME " + (1 + gameName) + " High Scores:", 70, 800, paint);
        if(SceneManager.highscoreScores[gameName][0] != 0) canvas.drawText("#1  " + SceneManager.highscore[gameName][0] + "   " + SceneManager.highscoreScores[gameName][0], 70, 1000, paint);
        if(SceneManager.highscoreScores[gameName][1] != 0) canvas.drawText("#2  " + SceneManager.highscore[gameName][1] + "   " + SceneManager.highscoreScores[gameName][1], 70, 1200, paint);
        if(SceneManager.highscoreScores[gameName][2] != 0) canvas.drawText("#3  " + SceneManager.highscore[gameName][2] + "   " + SceneManager.highscoreScores[gameName][2], 70, 1400, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 1;
            manager.resetScenes();
        }
        if (game1.isClicked((int) event.getX(), (int) event.getY())) {
            gameName = 0;
        }
        if (game2.isClicked((int) event.getX(), (int) event.getY())) {
            gameName = 1;
        }
        if (game3.isClicked((int) event.getX(), (int) event.getY())) {
            gameName = 2;
        }
    }
}