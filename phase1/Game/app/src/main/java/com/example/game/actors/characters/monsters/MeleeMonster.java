package com.example.game.actors.characters.monsters;

import com.example.game.algorithms.ChaseStrategy;
import com.example.game.algorithms.StrategyContext;
import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.player.Player;

import java.util.ArrayList;

class MeleeMonster extends Character {
    private StrategyContext context;

    MeleeMonster() {
        this.context = new StrategyContext(new ChaseStrategy());
    }

    public void update(Player player, ArrayList<SlimeMeleeMonster> collidableCharacters) {
            context.executeStrategy(player, this, collidableCharacters);

    }

}
