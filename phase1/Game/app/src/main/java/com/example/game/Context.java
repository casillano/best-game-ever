package com.example.game;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(Player player, Character character){
        strategy.move(player, character);
    }
}