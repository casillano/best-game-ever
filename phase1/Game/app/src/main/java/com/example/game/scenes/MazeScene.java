package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.game.design.Background;
import com.example.game.actors.Button;
import com.example.game.backend.Constants;
import com.example.game.actors.characters.player.Player;
import com.example.game.algorithms.MazeCreator;


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
  private MazeCreator mazeCreator;
  private Rect r;
  boolean firstDraw = true;

  MazeScene(Context context, SceneManager manager) {
    this.manager = manager;
    player = new Player(context, Constants.playerColor);
    background = new Background(context);
    player.changeRectangle(300, 550, 400, 650);
    playerPoint = new Point(player.getRectangle().centerX(), player.getRectangle().centerY());
    quitButton = new Button(850, 300, 100, 100, "X");
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
    }
  }


  @Override
  public void draw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(Color.BLACK);
    background.draw(canvas);
    quitButton.draw(canvas);
    mazeCreator.drawMaze(canvas);
    player.draw(canvas);

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
