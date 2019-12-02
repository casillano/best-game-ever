package com.example.game.scenes;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.content.SharedPreferences;

import com.example.game.gamecomponents.mazecreator.CollisionChecker;
import com.example.game.gamecomponents.mazecreator.MazeGenerator;
import com.example.game.design.Background;
import com.example.game.design.Button;

import java.util.ArrayList;

public class SceneManager {
    private final ArrayList<Scene> scenes;
    static int activeScene;
    static int nextScene;

    private final Context context;
    static private SharedPreferences pref;
    static private Editor editor;
    static private SurvivalScene game1;
    static private MenuScene menu;
    static private MazeScene maze;
    static private GlassScene game3;
    static private Loading loading;

    private ScoreBoardScene scoreScene;
    private CustomizationScene store;
    private WelcomeScene welcome;
    static private SignIn signIn;
    static private Login login;
    static private int xp;
    static private int xp1;
    static private int xp2;
    static private int xp3;
    static private String userInfo;
    static private String userName;
    static private String color;
    static String[][] highscore;
    static int[][] highscoreScores;

    private final MazeGenerator mazeGenerator;
    private final CollisionChecker collisionChecker;
    private Background background;
    private final Button quitButton;

    public SceneManager(Context context) {
        this.context = context;
        highscoreScores = new int[3][3];
        highscore = new String[3][3];
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        activeScene = 6;
        scenes = new ArrayList<>();
        addAllScenes();
        editor = pref.edit();
        userInfo = "";
        xp = 0;
        xp1 = 0;
        xp2 = 0;
        xp3 = 0;
        color = "blue";
        userName = "";

        mazeGenerator = new MazeGenerator();
        collisionChecker = new CollisionChecker();
        background = new Background(context, "grass");
        quitButton = new Button(850, 300, 100, 100, "X");
    }

    public void receiveTouch(MotionEvent event) {
        if (scenes.size() > 0) {
            scenes.get(activeScene).receiveTouch(event);
        }
    }

    public void update() {
        if (scenes.size() > 0) {
            scenes.get(activeScene).update();
        }
    }

    public void draw(Canvas canvas) {
        if (scenes.size() > 0) {
            scenes.get(activeScene).draw(canvas);
        }

    }

    void resetScenes() {
        xp += game1.getXp();
        xp += maze.getXp();
        xp += game3.getXp();
        xp2 += maze.getXp();
        xp1 += game1.getXp();
        xp3 += game3.getXp();
        color = store.getCostume();
        getHighScores();
        checkHighScore();
        menu.setXp(xp);
        editor.putInt(userInfo + "xp", xp);
        editor.putInt(userInfo + "xp1", xp1);
        editor.putInt(userInfo + "xp2", xp2);
        editor.putInt(userInfo + "xp3", xp3);
        editor.apply();
        scenes.clear();
        addAllScenes();
    }

    private void getHighScores() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                highscore[i][j] = pref.getString("highname" + i + "" + j, "");
                highscoreScores[i][j] = pref.getInt("highscore" + i + "" + j, 0);
            }
        }
    }

    private void checkHighScore() {
        if (game1.getXp() > 0) {
            swapScores(game1.getXp(), 0);
            setHighScores();
        } else if (maze.getXp() > 0) {
            swapScores(maze.getXp(), 1);
            setHighScores();
        } else if (game3.getXp() > 0) {
            swapScores(game3.getXp(), 2);
            setHighScores();
        }
    }

    private void setHighScores() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Scores: " + highscore[i][j] + highscoreScores[i][j]);
                editor.putString("highname" + i + "" + j, highscore[i][j]);
                editor.apply();
                editor.putInt("highscore" + i + "" + j, highscoreScores[i][j]);
                editor.apply();
            }
        }
    }

    private void swapScores(int score, int i) {
        if (score >= highscoreScores[i][0]) {
            highscoreScores[i][2] = highscoreScores[i][1];
            highscore[i][2] = highscore[i][1];
            highscoreScores[i][1] = highscoreScores[i][0];
            highscore[i][1] = highscore[i][0];
            highscoreScores[i][0] = score;
            highscore[i][0] = userName;
        } else if (score >= highscoreScores[i][1]) {
            highscoreScores[i][2] = highscoreScores[i][1];
            highscore[i][2] = highscore[i][1];
            highscoreScores[i][1] = score;
            highscore[i][1] = userName;
        } else if (score >= highscoreScores[i][2]) {
            highscoreScores[i][2] = score;
            highscore[i][2] = userName;
        }
    }

    private void addAllScenes() {
        login = new Login(context, this, new Background(context, "grass"));
        signIn = new SignIn(context, this);
        game1 = new SurvivalScene(context, this, background);
        menu = new MenuScene(this, new Background(context, "grass"));
        maze = new MazeScene(context, this, mazeGenerator, collisionChecker, background,
                quitButton);
        game3 = new GlassScene(context, this, background);
        loading = new Loading(background);

        store = new CustomizationScene(this, new Background(context, "store"));
        welcome = new WelcomeScene(this, new Background(context, "login"));
        scoreScene = new ScoreBoardScene(this, new Background(context, "grass"));
        scenes.add(login);
        scenes.add(menu);
        scenes.add(game1);
        scenes.add(maze);
        scenes.add(game3);
        scenes.add(store);
        scenes.add(welcome);
        scenes.add(signIn);
        scenes.add(scoreScene);
        scenes.add(loading);
    }

    int getXp() {
        return pref.getInt(userInfo + "xp", 0);
    }

    int getXp1() {
        return pref.getInt(userInfo + "xp1", 0);
    }

    int getXp2() {
        return pref.getInt(userInfo + "xp2", 0);
    }

    int getXp3() {
        return pref.getInt(userInfo + "xp3", 0);
    }

    static void setUserInfo(String name, String password) {
        userInfo = name + password;
        userName = name;
        color = pref.getString(userInfo + "color", "blue");
        xp = pref.getInt(userInfo + "xp", 0);
        xp1 = pref.getInt(userInfo + "xp1", 0);
        xp2 = pref.getInt(userInfo + "xp2", 0);
        xp3 = pref.getInt(userInfo + "xp3", 0);
        menu.setXp(xp);
        System.out.println("xp: " + xp);
        game1.setCostume(color);
        maze.setCostume(color);
        game3.setCostume(color);
    }

    static void registerUser(String user, String pass) {
        editor.putString(user + "password", pass);
        editor.apply();
        editor.putBoolean(user, true);
        editor.apply();
    }

    static boolean userExists(String user) {
        return pref.getBoolean(user, false);
    }

    static boolean validPassword(String user, String pass) {
        return pass.equals(pref.getString(user + "password", ""));
    }

    static void setCostume(String col) {
        editor.putString(userInfo + "color", col);
        color = col;
        game1.setCostume(color);
        maze.setCostume(color);
        game3.setCostume(color);
        editor.apply();
    }

    static String getCostume() {
        return pref.getString(userInfo + "color", "blue");
    }

    static String getUserName() {
        return userName;
    }

    static void changeUser() {
        login.resetUser();
    }

    public void setBackground(String type) {
        this.background = new Background(context, type);
    }
}
