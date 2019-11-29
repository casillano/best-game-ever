package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.game.design.Background;
import com.example.game.design.Button;
import com.example.game.backend.characters.player.Player;
import com.example.game.backend.mazecreator.MazeGenerator;

import java.util.ArrayList;


/* Scene for a maze game
 * Rules: You must make it to the finish line without touching the walls
 * If you touch the walls, you die and game is over.
 */

public class MazeScene implements Scene {

    private Background background;
    private Player player;
    private Point playerPoint;
    private Button quitButton;
    private SceneManager manager;
    private boolean gameOver = false, movingPlayer = false;
    private MazeGenerator mazeGenerator;
    private int xp;
    private Paint wallPaint;
    private Paint finishLinePaint;

    MazeScene(Context context, SceneManager manager) {
        this.manager = manager;
        wallPaint = new Paint();
        finishLinePaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(3);
        finishLinePaint.setColor(Color.RED);
        player = new Player(context, SceneManager.getCostume());
        background = new Background(context);
        player.changeRectangle(300, 550, 400, 650);
        playerPoint = new Point(player.getRectangle().centerX(), player.getRectangle().centerY());
        quitButton = new Button(850, 300, 100, 100, "X");
        mazeGenerator = new MazeGenerator();
        xp = 0;
    }

    private void drawMaze(Canvas canvas) {
        mazeGenerator.setupMaze(canvas);
        ArrayList<Rect> walls = mazeGenerator.getWalls();
        Rect finishLine = mazeGenerator.getFinishLine();
        for (int i = 0; i < walls.size(); i++) {
            canvas.drawRect(walls.get(i), wallPaint);
        }
        canvas.drawRect(finishLine, finishLinePaint);
    }

    @Override
    public void update() {
        background.update();
        player.update(playerPoint);
        //pop up showing loser, then return to main menu
        if (mazeGenerator.checkCollisions(player)) {
            gameOver = true;
            terminate();
            manager.resetScenes();

        }
        //pop up showing winner
        if (mazeGenerator.checkFinished(player)) {
            xp = 150;
            gameOver = true;
            terminate();
            manager.resetScenes();
        }
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        background.draw(canvas);
        quitButton.draw(canvas);
        mazeGenerator.setupMaze(canvas);
        player.draw(canvas);
        drawMaze(canvas);
    }

    int getXp() {
        return xp;
    }

    void setCostume(String color) {
        player.setCostume(color);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;

        }
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            terminate();
            manager.resetScenes();
        }
    }
}
