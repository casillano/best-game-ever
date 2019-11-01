package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Login implements Scene{
    private String email;
    private String password;
    private String passwordDisplay;
    private Button signIn;
    private ArrayList<Button> buttons;
    private HashMap DB;
    private Background background;
    private String userInput = "";
    private int counter;
    private Button enter;
    private Button erase;
    private boolean pass;
    private Button passwordButton;
    private Button emailButton;
    private String cursorEmail = "|";
    private String cursorPassword = "";
    private int count = 0;

    Login(Context context, SceneManager manager) {
        email = "";
        password = "";
        passwordDisplay = "";
        pass = false;
        background = new Background(context);
        buttons = new ArrayList<>();
        DB = new HashMap();
        String s = "1234567890qwertyuiopasdfghjklzxcvbnm.@,";
//        Making buttons for numbers
        for (int i = 0; i < 10; i++) {
            buttons.add(new Button(105 * i +18, 1180, 100, 100, String.valueOf(s.charAt(i))));
        }
//        Making buttons for first 10 qwerty characters
        for (int i = 0; i < 10; i++) {
            buttons.add(new Button(105 * i + 18, 1300, 100, 100, String.valueOf(s.charAt(10 + i))));
        }
//        Making buttons for next 9 qwerty values
        for (int i = 0; i < 9; i++) {
            buttons.add(new Button(105 * i +70, 1420, 100, 100, String.valueOf(s.charAt(20 + i))));
        }
//        Making buttons for last 9 qwerty values
        for (int i = 0; i < 9; i++) {
            buttons.add(new Button(105 * i +70, 1540, 100, 100, String.valueOf(s.charAt(29 + i))));
        }
//        Making the SignIn button
        signIn = new Button(30, 1900, 1000, 100, "Sign in / Login");
        erase = new Button(800, 1700, 200, 100, "<--");
        emailButton = new Button(30, 700, 1000, 100, "e-mail:");
        passwordButton = new Button(30, 940, 1000, 100, "password:");
    }

    public void update() {
        background.update();
        if(count % 20 < 10){
            cursorEmail = "";
        }
        else{
            if(!pass) cursorEmail = "|";
        }
        if(count % 20 < 10){
            cursorPassword = "";
        }
        else{
            if(pass) cursorPassword = "|";
        }
        count++;
    }

    //    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        for (Button b : buttons) {
            b.draw(canvas);
        }
//        signUp.draw(canvas);
        signIn.draw(canvas);
        erase.draw(canvas);
        passwordButton.draw(canvas);
        emailButton.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(70);
        Paint p1 = new Paint();
        Paint p2 = new Paint();
        p1.setColor(Color.rgb(0, 0, 0));
        p2.setColor(Color.rgb(255, 255, 255));
        canvas.drawRect(28, 823, 1032, 927, p1);
        canvas.drawRect(30, 825, 1030, 925, p2);
        canvas.drawRect(28, 1053, 1032, 1157, p1);
        canvas.drawRect(30, 1055, 1030, 1155, p2);
        canvas.drawText("" + email + cursorEmail, 30, 890, paint);
        canvas.drawText("" + passwordDisplay + cursorPassword, 30, 1120, paint);
    }

    public void receiveTouch(MotionEvent event) {
        for (Button b : buttons) {
            if (b.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    if(pass){
                        password+= b.getName();
                        passwordDisplay += "*";
                    }
                    else{
                        email+= b.getName();
                    }
                }
                counter += 1;
            }
        }

        if (emailButton.isClicked((int) event.getX(), (int) event.getY()-100)) {
            pass = false;
        }
        if (passwordButton.isClicked((int) event.getX(), (int) event.getY()-100)) {
            pass = true;
        }
        if (erase.isClicked((int) event.getX(), (int) event.getY())) {
            if(pass){
                password = "";
                passwordDisplay = "";
            }
            else{
                email = "";
            }
        }
        if (signIn.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 1;
            SceneManager.setUserInfo(email, password);
        }
    }

    void resetUser(){
        password = "";
        email = "";
        passwordDisplay = "";
    }
    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}
