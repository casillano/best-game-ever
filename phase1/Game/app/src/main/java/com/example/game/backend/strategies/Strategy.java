package com.example.game.backend.strategies;

import com.example.game.backend.characters.Character;
import com.example.game.backend.characters.monsters.SlimeMeleeMonster;
import com.example.game.backend.characters.player.Player;

import java.util.ArrayList;

public interface Strategy {
    void move(Player player, Character character, ArrayList<SlimeMeleeMonster> collidableCharacters);
}
