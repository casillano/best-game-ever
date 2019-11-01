package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Login {
    private String email;
    private String password;
    private Button signIn;

    private ArrayList<Button> buttons;
    private HashMap DB;
    private Background background;
    private Player player;
    private Point playerPoint;
    private String userInput = "";
    private int counter;
    private Button enter;
    private Button erase;

    public Login(Context context, SceneManager manager) {
        email = "";
        password = "";
        buttons = new ArrayList<>();
        DB = new HashMap();
        String s = "qwertyuiopasdfghjklzxcvbnm.@,";
//        Making buttons for first 10 qwerty characters
        for (int i = 0; i < 10; i++) {
            buttons.add(new Button(100 * i, 1500, 100, 100, String.valueOf(s.charAt(i))));
        }
//        Making buttons for next 9 qwerty values
        for (int i = 0; i < 10; i++) {
            buttons.add(new Button(100 * i, 1700, 100, 100, String.valueOf(s.charAt(10 + i))));
        }
//        Making buttons for last 9 qwerty values
        for (int i = 0; i < 9; i++) {
            buttons.add(new Button(130 * i, 1900, 100, 100, String.valueOf(s.charAt(19 + i))));
        }
//        Making the SignIn button
        signIn = new Button(100, 1000, 150, 150, "SignIn / SignUp (Format: Email, Password");
        enter = new Button(200, 2100, 100, 100, "Enter");
        erase = new Button(400, 2100, 100, 100, "Erase");

    }

    public void update() {

        background.update();
        player.update(playerPoint);
    }

    //    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        for (Button b : buttons) {
            b.draw(canvas);
        }
//        signUp.draw(canvas);
        signIn.draw(canvas);
        enter.draw(canvas);
        erase.draw(canvas);
    }

    public void receiveTouch(MotionEvent event) {
        for (Button b : buttons) {
            if (b.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    userInput += b.getName();
                }
                counter += 1;
            }
        }
        String[] temp = userInput.split(",");
        email = temp[0].trim();
        password = temp[1].trim();

        if (enter.isClicked((int) event.getX(), (int) event.getY())) {
            try {
                if (DB.containsKey(email) && password.equals(DB.get(email))) {
                    // change scene to move into game
                } else if (DB.containsKey(email) && !password.equals(DB.get(email))) {
                    // "try again"
                } else {
                    DB.put(email, password);
                }
            } catch (NullPointerException e) {
            }

            if (erase.isClicked((int) event.getX(), (int) event.getY())) {
                userInput = "";
            }
        }
    }
}
