package com.example.game;

import android.graphics.Rect;

import java.util.ArrayList;

class MazeCollisions {

  //Error. If the character collides with the wall, it becomes stuck.
  //Need to get mouse movement to check if the character moves away from the wall

  static boolean checkCollisions(ArrayList<Rect> walls, Player player) {

    Rect playerRect = player.getRectangle();
    Rect collisionBox = new Rect(playerRect.left + 50, playerRect.top + 50, playerRect.right - 50,
            playerRect.bottom - 50);

    for (int i = 0; i < walls.size(); i++) {
      if (Rect.intersects(collisionBox, walls.get(i))) {
        return true;
      }
    }
    return false;
  }

  static boolean checkFinished(Player player, Rect finish) {
    return Rect.intersects(player.getRectangle(), finish);
  }
}
