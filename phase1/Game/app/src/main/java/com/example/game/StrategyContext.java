package com.example.game;

public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(Player player, Character character){
        strategy.move(player, character);
    }
}