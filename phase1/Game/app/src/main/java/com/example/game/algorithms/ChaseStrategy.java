package com.example.game.algorithms;

import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.player.Player;

public class ChaseStrategy implements Strategy {
    @Override
    public void move(Player player, Character character) {
        double oldLeft = character.getRectangle().left;
        // Find the vector from the character to the player
        float[] normal = new float[2];
        normal[0] = player.getRectangle().centerX() - character.getRectangle().centerX();
        normal[1] = player.getRectangle().centerY() - character.getRectangle().centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        //Checks if the monster is within attacking distance
        if (magnitude <= (float) (character.getRectangle().width() / 2 + player.getRectangle().width() / 2)) {
            player.healthBar.takeDamage(character.damage);
        }
        if (magnitude < character.speed) {
            character.getAnimationManager().playAnimation(0);
            return;
        }
        //Moves the character towards the player
        chasePlayer(normal, magnitude, character);

        //Plays the appropriate animation
        playMovementAnimation(oldLeft, character);
    }

    private void chasePlayer(float[] normal, float magnitude, Character character) {
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int) (un[0] * character.speed);
        int move_y = (int) (un[1] * character.speed);

        character.changeRectangle(character.getRectangle().centerX() + move_x - character.getRectangle().width() / 2,
                (character.getRectangle().centerY() + move_y) - character.getRectangle().height() / 2,
                (character.getRectangle().centerX() + move_x) + character.getRectangle().width() / 2,
                (character.getRectangle().centerY() + move_y) + character.getRectangle().height() / 2);
        character.healthBar.move(move_x, move_y);
    }
    
    private  void playMovementAnimation(double oldLeft,Character character) {
        int state = 0; // 0 blueidle, 1 walking , 2 walking left
        if (character.getRectangle().left - oldLeft > 0) {
            state = 1;
        } else if (character.getRectangle().left - oldLeft < 0) {
            state = 2;
        }

        character.getAnimationManager().playAnimation(state);
        character.getAnimationManager().update();
    }
}
