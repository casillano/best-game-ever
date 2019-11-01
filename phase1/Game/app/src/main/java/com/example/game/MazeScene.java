package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class MazeScene implements Scene {

  private Background background;
  private Player player;
  private Point playerPoint;
  private Button quitButton;
  private int score = 0;
  private Rect r = new Rect();
  private SceneManager manager;
  private boolean gameOver = false, winner = false, movingPlayer = false;
  private MazeCreator mazeCreator;
  private int xp;

  MazeScene(Context context, SceneManager manager) {
    this.manager = manager;
    xp = 0;
    player = new Player(context, Constants.playerColor);
    background = new Background(context);
    playerPoint = new Point(50, 50);
    quitButton = new Button(850, 50, 100, 100, "X");
    mazeCreator = new MazeCreator();
  }

  @Override
  public void update() {
    background.update();
    player.update(playerPoint);

    //pop up showing loser, then return to main menu
    if (mazeCreator.checkCollisions(player)) {
      gameOver = true;
      terminate();
      manager.resetScenes();

    }
    //pop up showing winner
    if (mazeCreator.checkFinished(player)) {
      gameOver = true;
      terminate();
      manager.resetScenes();
      winner = true;
    }
  }

  private void reset() {

    //go to the menu scene after 3 seconds

    new java.util.Timer().schedule(
            new java.util.TimerTask() {
              @Override
              public void run() {
                terminate();
                xp = 0;
                manager.resetScenes();
              }
            }, 3000
    );

  }


  @Override
  public void draw(Canvas canvas) {
    background.draw(canvas);
    quitButton.draw(canvas);
    player.draw(canvas);
    mazeCreator.drawMaze(canvas);

//    if (gameOver) {
//      canvas.drawColor(Color.GREEN);
//      Paint paint = new Paint();
//      paint.setTextSize(30);
//      paint.setColor(Color.BLACK);
//      if (!winner) {
//        drawGameOverText(canvas, paint, "Game Over ¯\\_(ツ)_/¯");
//      } else {
//        drawGameOverText(canvas, paint, "Winner!");
//      }
//    }
  }


  private void drawGameOverText(Canvas canvas, Paint paint, String text) {
    paint.setTextAlign(Paint.Align.LEFT);
    canvas.getClipBounds(r);
    int cHeight = r.height();
    int cWidth = r.width();
    paint.getTextBounds(text, 0, text.length(), r);
    float x = cWidth / 2f - r.width() / 2f - r.left;
    float y = cHeight / 2f + r.height() / 2f - r.bottom;
    canvas.drawText(text, x, y, paint);

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
