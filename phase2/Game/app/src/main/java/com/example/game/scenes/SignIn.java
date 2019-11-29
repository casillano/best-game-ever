package com.example.game.scenes;

import android.content.Context;

import com.example.game.design.Button;

public class SignIn extends Login{

    SignIn(Context context, SceneManager manager) {
        super(context, manager);
        super.buttonText = "Sign in";
        super.message = "User already exists.";
        super.newUser = true;
        super.signIn = new Button(30, 1900, 600, 100, buttonText);
    }
}
