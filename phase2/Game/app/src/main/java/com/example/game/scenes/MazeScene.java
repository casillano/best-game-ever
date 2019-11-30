package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.game.backend.mazecreator.CollisionChecker;
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
    private CollisionChecker collisionChecker;
    private int xp;
    private boolean firstDraw = true;
    private Paint wallPaint;
    private Paint finishLinePaint;
    private Paint textPaint;

    MazeScene(Context context, SceneManager manager, MazeGenerator mazeGenerator,
              CollisionChecker collisionChecker, Background background, Button quitButton) {
        this.manager = manager;
        wallPaint = new Paint();
        finishLinePaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(3);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(90);
        textPaint.setTypeface(Typeface.SANS_SERIF);
        finishLinePaint.setColor(Color.RED);
        player = new Player(context, SceneManager.getCostume());
        this.background = background;
        player.changeRectangle(220, 570, 320, 670);
        playerPoint = new Point(player.getRectangle().centerX(), player.getRectangle().centerY());
        this.quitButton = quitButton;
        this.mazeGenerator = mazeGenerator;
        this.collisionChecker = collisionChecker;
        xp = 0;
    }

    private void setCollisionWalls(ArrayList<Rect> walls, Rect finishLine) {
        collisionChecker.setCollisionChecker(walls, finishLine);
    }


    private void drawMaze(Canvas canvas) {

        /*These two if statements cannot be combined because the walls and finishLine variables
        are needed by the canvas.drawRect method as well as the collision checker setup.
        If the statements were placed in the loop, setCollisionWalls would not know if the
        variables had been instantiated or not.
         */

        /*this if statement is used to check if the current drawMaze call is the first or not.
        If it isn't the first call, then the maze has already been generated and collision detection
        has been set up, so there is no need to call them again.
         */

        if (firstDraw) {
            mazeGenerator.setupMaze(canvas);
        }
        ArrayList<Rect> walls = mazeGenerator.getWalls();
        Rect finishLine = mazeGenerator.getFinishLine();

        if (firstDraw) {
            setCollisionWalls(walls, finishLine);
            firstDraw = false;
        }

        for (int i = 0; i < walls.size(); i++) {
            canvas.drawRect(walls.get(i), wallPaint);
        }
        canvas.drawRect(finishLine, finishLinePaint);
    }

    private void resetMazeComponents() {
        mazeGenerator.resetGenerator();
        mazeGenerator.resetGeneratorFirstDraw();
        collisionChecker.resetCollisionChecker();
    }

    @Override
    public void update() {
        background.update();
        player.update(playerPoint);
        if (collisionChecker.checkFinished(player)) {
            xp = 150;
            gameOver = true;
            terminate();
            resetMazeComponents();
            manager.resetScenes();
        } else if (collisionChecker.checkCollisions(player)) {
            gameOver = true;
            terminate();
            resetMazeComponents();
            manager.resetScenes();

        }
    }


    @Override
    public void draw(Canvas canvas) {

        background.draw(canvas);
        quitButton.draw(canvas);
        player.draw(canvas);
        canvas.drawText("If you hit a wall, you die :(", 30, 150, textPaint);
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