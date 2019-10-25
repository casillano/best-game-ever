package com.example.game;

class MeleeMonster extends Character {

    void update(Player player) {
        double oldLeft = getRectangle().left;
        float[] normal = new float[2];
        normal[0] = player.getRectangle().centerX() - getRectangle().centerX();
        normal[1] = player.getRectangle().centerY() - getRectangle().centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        if (magnitude <= (float) (getRectangle().width() / 2 + player.getRectangle().width() / 2)) {
            player.healthBar.take_damage(this.damage);
        }
        if (magnitude < speed) {
            getAnimationManager().playAnimation(0);
            return;
        }
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int) (un[0] * speed);
        int move_y = (int) (un[1] * speed);

        changeRectangle(getRectangle().centerX() + move_x - getRectangle().width() / 2,
                (getRectangle().centerY() + move_y) - getRectangle().height() / 2,
                (getRectangle().centerX() + move_x) + getRectangle().width() / 2,
                (getRectangle().centerY() + move_y) + getRectangle().height() / 2);
        healthBar.move(move_x, move_y);

        int state = 0; // 0 idle, 1 walking , 2 walking left
        if (getRectangle().left - oldLeft > 0) {
            state = 1;
        } else if (getRectangle().left - oldLeft < 0) {
            state = 2;
        }

        getAnimationManager().playAnimation(state);
        getAnimationManager().update();

    }
}
