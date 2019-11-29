package com.example.game.backend.characters.monsters;

import com.example.game.backend.strategies.ChaseStrategy;
import com.example.game.backend.strategies.StrategyContext;
import com.example.game.backend.characters.Character;
import com.example.game.backend.characters.player.Player;

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
