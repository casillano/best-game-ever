package com.example.game.scenes;

import android.content.Context;

import com.example.game.design.Background;
import com.example.game.design.Button;

class SignIn extends Login{

    SignIn(Context context, SceneManager manager) {
        super(context, manager, new Background(context, "grass"));
        super.buttonText = "Sign in";
        super.message = "User already exists.";
        super.newUser = true;
        super.signIn = new Button(30, 1900, 600, 100, buttonText);
    }
}
