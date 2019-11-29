package com.example.game.backend.characters.monsters;

import com.example.game.backend.strategies.StrafeStrategy;
import com.example.game.backend.strategies.StrategyContext;
import com.example.game.backend.characters.Character;
import com.example.game.backend.characters.player.Player;

import java.util.ArrayList;

class StrafingMonster extends Character {
    private StrategyContext context;

    StrafingMonster() {
        this.context = new StrategyContext(new StrafeStrategy());
    }

    public void update(Player player, ArrayList<SlimeMeleeMonster> collidableCharacters) {
        context.executeStrategy(player, this, collidableCharacters);

    }

}

