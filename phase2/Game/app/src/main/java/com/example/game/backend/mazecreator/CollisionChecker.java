package com.example.game.backend.mazecreator;

import android.graphics.Rect;

import com.example.game.backend.characters.player.Player;

import java.util.ArrayList;

public class CollisionChecker {

    //Error. If the character collides with the wall, it becomes stuck.
    //Need to get mouse movement to check if the character moves away from the wall
    private ArrayList<Rect> walls;
    private Rect finishLine;

    public CollisionChecker() {
        walls = new ArrayList<>();

    }

    public void setCollisionChecker(ArrayList<Rect> walls, Rect finishLine) {
        this.walls.clear();
        this.walls.addAll(walls);
        this.finishLine = finishLine;

    }

    public boolean checkCollisions(Player player) {
        if (walls.size() > 0) {
            return checkPlayerCollision(player);
        }
        return false;
    }

    private boolean checkPlayerCollision(Player player) {
        Rect playerRect = player.getRectangle();
        Rect collisionBox = new Rect(playerRect.left + 30, playerRect.top + 30, playerRect.right - 30,
                playerRect.bottom - 30);

        for (int i = 0; i < walls.size(); i++) {
            if (collisionBox.intersect(walls.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFinished(Player player) {

        Rect playerRect = player.getRectangle();
        Rect collisionBox = new Rect(playerRect.left + 30, playerRect.top + 30, playerRect.right - 30,
                playerRect.bottom - 30);
        if (walls.size() > 0) {
            return (collisionBox.intersect(finishLine));
        }
        return false;
    }
}
