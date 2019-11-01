package com.example.game;

import android.graphics.Rect;

import java.util.ArrayList;

class MazeCollisions {

  //Error. If the character collides with the wall, it becomes stuck.
  //Need to get mouse movement to check if the character moves away from the wall

  static boolean checkCollisions(ArrayList<Rect> walls, Player player) {

    for (int i = 0; i < walls.size(); i++) {
      if (Rect.intersects(player.getRectangle(), walls.get(i))) {
        return true;
      }
    }
    return false;
  }

  static boolean checkFinished(Player player, Rect finish) {
    return Rect.intersects(player.getRectangle(), finish);
  }
}
