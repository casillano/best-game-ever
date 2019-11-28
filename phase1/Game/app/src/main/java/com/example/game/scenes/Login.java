package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.game.design.Button;
import com.example.game.design.Background;

import java.util.ArrayList;


public class Login implements Scene {
    private String email;
    private String password;
    private String passwordDisplay;
    Button signIn;
    private ArrayList<Button> buttons;
    private Background background;
    private int counter;
    private Button erase;
    private boolean pass;
    private Button passwordButton;
    private Button emailButton;
    private String cursorEmail = "|";
    private String cursorPassword = "";
    String message;
    private boolean noUserName = false;
    private int count = 0;
    String buttonText;
    private boolean noUser = false;
    boolean newUser = false;
    private Button quitButton;

    Login(Context context, SceneManager manager) {
        email = "";
        password = "";
        passwordDisplay = "";
        pass = false;
        buttonText = "Login";
        background = new Background(context);
        quitButton = new Button(850, 50, 100, 100, "X");
        buttons = new ArrayList<>();
        String s = "1234567890qwertyuiopasdfghjklzxcvbnm.@,";
        message = "User doesn't exist";
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
        signIn = new Button(30, 1900, 600, 100, buttonText);
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
        quitButton.draw(canvas);
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
        if(noUser) canvas.drawText(message, 30, 240, paint);
        if(noUserName) {
            canvas.drawText("You need to enter a valid", 30, 300, paint);
            canvas.drawText("username and password", 30, 360, paint);
        }
    }

    public void receiveTouch(MotionEvent event) {
        for (Button b : buttons) {
            if (b.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    if(pass){
                        password += b.getName();
                        passwordDisplay += "*";
                    }
                    else{
                        email += b.getName();
                    }
                }
                counter += 1;
            }
        }

        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.ACTIVE_SCENE = 6;
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
            if(!email.equals("") && !password.equals("")) {
                if(email.contains("@")){
                    if(!newUser == checkValidUserName(email)) {
                        if(newUser) {
                            SceneManager.registerUser(email, password);
                            SceneManager.setUserInfo(email, password);
                            SceneManager.ACTIVE_SCENE = 1;
                        }
                        else {
                            if(SceneManager.validPassword(email, password)) {
                                SceneManager.setUserInfo(email, password);
                                SceneManager.ACTIVE_SCENE = 1;
                            }
                            else{
                                noUserName = true;
                            }
                        }
                    }
                    else{
                        noUser = true;
                    }
                }
                else{
                    noUserName = true;
                }
            }
            else{
                noUserName = true;
            }
        }
    }

    private boolean checkValidUserName(String name){
        return SceneManager.userExists(name);
    }

    void resetUser(){
        password = "";
        email = "";
        passwordDisplay = "";
        noUserName = false;
    }
    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}
