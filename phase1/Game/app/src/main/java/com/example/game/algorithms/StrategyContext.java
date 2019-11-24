package com.example.game.algorithms;

import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.monsters.SlimeMeleeMonster;
import com.example.game.actors.characters.player.Player;

import java.util.ArrayList;

public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(Player player, Character character,
                                ArrayList<SlimeMeleeMonster> collidableCharacters) {
        strategy.move(player, character, collidableCharacters);
    }
}