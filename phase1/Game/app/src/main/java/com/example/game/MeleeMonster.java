package com.example.game;

class MeleeMonster extends Character {
    private Context context;

    MeleeMonster() {
        this.context = new Context(new ChaseStrategy());
    }

    void update(Player player) {
        context.executeStrategy(player, this);

    }
}
