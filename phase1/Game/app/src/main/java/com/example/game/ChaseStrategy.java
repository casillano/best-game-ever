package com.example.game;

public class ChaseStrategy implements Strategy {
    @Override
    public void move(Player player, Character character) {
        double oldLeft = character.getRectangle().left;
        float[] normal = new float[2];
        normal[0] = player.getRectangle().centerX() - character.getRectangle().centerX();
        normal[1] = player.getRectangle().centerY() - character.getRectangle().centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        if (magnitude <= (float) (character.getRectangle().width() / 2 + player.getRectangle().width() / 2)) {
            player.healthBar.takeDamage(character.damage);
        }
        if (magnitude < character.speed) {
            character.getAnimationManager().playAnimation(0);
            return;
        }
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
