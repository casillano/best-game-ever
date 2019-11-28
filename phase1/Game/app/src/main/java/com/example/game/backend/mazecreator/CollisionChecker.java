package com.example.game.backend.mazecreator;

import android.graphics.Rect;

import com.example.game.backend.characters.player.Player;

import java.util.ArrayList;

class CollisionChecker {

  //Error. If the character collides with the wall, it becomes stuck.
  //Need to get mouse movement to check if the character moves away from the wall

  static boolean checkCollisions(ArrayList<Rect> walls, Player player) {

    Rect playerRect = player.getRectangle();
    Rect collisionBox = new Rect(playerRect.left + 40, playerRect.top + 40, playerRect.right - 40,
            playerRect.bottom - 40);


    for (int i = 0; i < walls.size(); i++) {
      if (collisionBox.intersect(walls.get(i))) {
        return true;
      }
    }
    return false;
  }

  static boolean checkFinished(Player player, Rect finish) {

    Rect playerRect = player.getRectangle();
    Rect collisionBox = new Rect(playerRect.left + 40, playerRect.top + 40, playerRect.right - 40,
            playerRect.bottom - 40);

    return (collisionBox.intersect(finish));
  }
}
