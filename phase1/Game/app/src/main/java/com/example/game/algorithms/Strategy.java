package com.example.game.algorithms;

import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.player.Player;

public interface Strategy {
    public void move(Player player, Character character);
}
