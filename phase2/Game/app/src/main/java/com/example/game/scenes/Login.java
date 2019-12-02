package com.example.game.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.game.gamecomponents.Builder;
import com.example.game.design.Button;
import com.example.game.design.Background;

import java.util.ArrayList;


public class Login implements Scene {
    private String email;
    private String password;
    private String passwordDisplay;
    Button signIn;
    private final ArrayList<Button> buttons;
    private final Background background;
    private int counter;
    private int counter2;
    private final Button erase;
    private boolean pass;
    private final Button passwordButton;
    private final Button emailButton;
    private String cursorEmail = "|";
    private String cursorPassword = "";
    private final SceneManager sManager;
    String message;
    private boolean noUserName = false;
    private int count = 0;
    String buttonText;
    private boolean noUser = false;
    boolean newUser = false;
    private final Button quitButton;

    Login(SceneManager manager, Background background) {
        sManager = manager;
        email = "";
        password = "";
        passwordDisplay = "";
        pass = false;
        buttonText = "Login";
        this.background = background;
        quitButton = new Button(750, 50, 300, 100, "BACK");
        buttons = Builder.buildEmailKB();
        message = "User doesn't exist";

//        Making the SignIn button
        signIn = new Button(30, 1900, 600, 100, buttonText);
        erase = new Button(800, 1700, 200, 100, "<--");
        emailButton = new Button(30, 700, 1000, 100, "e-mail:");
        passwordButton = new Button(30, 940, 1000, 100, "password:");
    }

    public void update() {
        background.update();
        if (count % 20 < 10) {
            cursorEmail = "";
        } else {
            if (!pass) cursorEmail = "|";
        }
        if (count % 20 < 10) {
            cursorPassword = "";
        } else {
            if (pass) cursorPassword = "|";
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
        if (noUser) canvas.drawText(message, 30, 240, paint);
        if (noUserName) {
            canvas.drawText("You need to enter a valid", 30, 300, paint);
            canvas.drawText("e-mail and password", 30, 360, paint);
        }
    }

    public void receiveTouch(MotionEvent event) {
        for (Button b : buttons) {
            if (b.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    if (pass) {
                        password += b.getName();
                        passwordDisplay += "*";
                    } else {
                        email += b.getName();
                    }
                }
                counter += 1;
            }
        }

        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.activeScene = 6;
        }

        if (emailButton.isClicked((int) event.getX(), (int) event.getY() - 100)) {
            pass = false;
        }
        if (passwordButton.isClicked((int) event.getX(), (int) event.getY() - 100)) {
            pass = true;
        }
        if (erase.isClicked((int) event.getX(), (int) event.getY())) {
            if (counter2 % 2 == 0) {
                if (pass) {
                    if (password.length() != 0) {
                        password = password.substring(0, password.length() - 1);
                        passwordDisplay = passwordDisplay.substring(0, passwordDisplay.length() - 1);
                    }
                } else {
                    if (email.length() != 0) email = email.substring(0, email.length() - 1);
                }
            }
            counter2 += 1;
        }
        if (signIn.isClicked((int) event.getX(), (int) event.getY())) {
            if (!email.equals("") && !password.equals("")) {
                if (email.contains("@")) {
                    if (!newUser == checkValidUserName(email)) {
                        if (newUser) {
                            SceneManager.registerUser(email, password);
                            SceneManager.setUserInfo(email, password);
                            sManager.resetScenes();
                            SceneManager.nextScene = 1;
                            SceneManager.activeScene = 9;
                        } else {
                            if (SceneManager.validPassword(email, password)) {
                                SceneManager.setUserInfo(email, password);
                                sManager.resetScenes();
                                SceneManager.nextScene = 1;
                                SceneManager.activeScene = 9;
                            } else {
                                noUserName = true;
                            }
                        }
                    } else {
                        noUser = true;
                    }
                } else {
                    noUserName = true;
                }
            } else {
                noUserName = true;
            }
        }
    }

    private boolean checkValidUserName(String name) {
        return SceneManager.userExists(name);
    }

    void resetUser() {
        password = "";
        email = "";
        passwordDisplay = "";
        noUserName = false;
    }

    @Override
    public void terminate() {
        SceneManager.activeScene = 0;
    }
}
