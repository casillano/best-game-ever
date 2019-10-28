package com.example.game;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;

public class MazePanel extends Panel {

    private MainThread thread;
    private Background background;
    private Player player;
    private Point playerPoint;
    private SlimeMeleeMonster slimeMonster;
    private Button quitButton;
    private MainActivity myContext;

    public MazePanel(Context context) {
        super(context);
        myContext = (MainActivity) context;
        getHolder().addCallback(this);
        Display display = ((MainActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Constants.DISPLAY_SIZE = size;

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        player = new Player(context);
        slimeMonster = new SlimeMeleeMonster(context);
        background = new Background(context);
        playerPoint = new Point(size.x / 2, size.y);
        quitButton = new Button(850, 50, 100, 100, "X");
    }
}
