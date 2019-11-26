package com.example.game.actors.characters.monsters;

import com.example.game.algorithms.StrafeStrategy;
import com.example.game.algorithms.StrategyContext;
import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.player.Player;

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

