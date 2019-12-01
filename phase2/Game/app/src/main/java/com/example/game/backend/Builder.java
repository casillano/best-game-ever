package com.example.game.backend;

import android.content.Context;
import com.example.game.backend.characters.monsters.SlimeMeleeMonster;
import java.util.ArrayList;
import java.util.Random;
import com.example.game.design.Button;

public class Builder {

//  Builds and returns an array of SlimeMeleeMonsters
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

//  Builds and returns an array of SlimeMeleeMonsters
    public static Button[] buildNumericKB() {
        Button[] buttons = new Button[10];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(100*i + 37, 1500, 100, 100, i+"");
        }
        return buttons;
    }
}
