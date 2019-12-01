package com.example.game.backend;

import android.content.Context;

import com.example.game.backend.characters.monsters.SlimeMeleeMonster;

import java.util.ArrayList;
import java.util.Random;

public class SlimeBuilder {

    public static ArrayList<SlimeMeleeMonster> buildSlime(Context context) {
        ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();
        Random rand = new Random();
        int numMonsters = rand.nextInt(20);

        for (int i = 0; i <= numMonsters; i++) {
            SlimeMeleeMonster m = new SlimeMeleeMonster(context, rand.nextInt(1000), rand.nextInt(1000));
            monsters.add(m);
        }
        return monsters;
    }
}
