package com.example.game;

class MeleeMonster extends Character {
    private StrategyContext context;

    MeleeMonster() {
        this.context = new StrategyContext(new ChaseStrategy());
    }

    void update(Player player) {
        context.executeStrategy(player, this);

    }
}
