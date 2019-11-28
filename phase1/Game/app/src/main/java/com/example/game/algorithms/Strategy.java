package com.example.game.algorithms;

import com.example.game.actors.characters.Character;
import com.example.game.actors.characters.monsters.SlimeMeleeMonster;
import com.example.game.actors.characters.player.Player;

import java.util.ArrayList;

public interface Strategy {
    void move(Player player, Character character, ArrayList<SlimeMeleeMonster> collidableCharacters);
}
