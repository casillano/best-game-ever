package com.example.game.algorithms;

import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.player.Player;

public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(Player player, Character character) {
        strategy.move(player, character);
    }
}