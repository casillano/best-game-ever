package com.example.game.backend.strategies;

import com.example.game.backend.characters.Character;
import com.example.game.backend.characters.monsters.SlimeMeleeMonster;
import com.example.game.backend.characters.player.Player;

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