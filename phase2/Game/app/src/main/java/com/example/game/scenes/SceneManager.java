package com.example.game.scenes;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SceneManager {
  private ArrayList<Scene> scenes;
  static int ACTIVE_SCENE;
  private Context context;
  static private SharedPreferences pref;
  static private Editor editor;
  static private GameOneScene game1;
  static private MenuScene menu;
  static private MazeScene maze;
  static private GlassScene game3;
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

  public SceneManager(Context context) {
    this.context = context;
    pref = PreferenceManager.getDefaultSharedPreferences(context);
    pref.edit().clear().commit();
    ACTIVE_SCENE = 6;
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
  }

  public void receiveTouch(MotionEvent event) {
    if (scenes.size() > 0) {
      scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }
  }

  public void update() {
    if (scenes.size() > 0) {
      scenes.get(ACTIVE_SCENE).update();
    }
  }

  public void draw(Canvas canvas) {
    if (scenes.size() > 0) {
      scenes.get(ACTIVE_SCENE).draw(canvas);
    }

  }

  public void resetScenes() {
    xp += game1.getXp();
    xp += game3.getXp();
    xp1 += game1.getXp();
    xp3 += game3.getXp();
    color = store.getCostume();
    menu.setXp(xp);
    editor.putInt(userInfo + "xp", xp);
    editor.putInt(userInfo + "xp1", xp1);
    editor.putInt(userInfo + "xp3", xp3);
    editor.apply();
    scenes.clear();
    addAllScenes();
  }

  void addAllScenes() {
    login = new Login(context, this);
    signIn = new SignIn(context, this);
    game1 = new GameOneScene(context, this);
    menu = new MenuScene(context, this);
    maze = new MazeScene(context, this);
    game3 = new GlassScene(context, this);
    store = new CustomizationScene(context, this);
    welcome = new WelcomeScene(context, this);
    scenes.add(login);
    scenes.add(menu);
    scenes.add(game1);
    scenes.add(maze);
    scenes.add(game3);
    scenes.add(store);
    scenes.add(welcome);
    scenes.add(signIn);
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
    xp3 = pref.getInt(userInfo + "xp3", 0);
    menu.setXp(xp);
    System.out.println("xp: " + xp);
    game1.setCostume(color);
    maze.setCostume(color);
    game3.setCostume(color);
  }

  static void registerUser(String user, String pass){
    editor.putString(user + "password", pass);
    editor.apply();
    editor.putBoolean(user, true);
    editor.apply();
  }

  static boolean userExists(String user){
    return pref.getBoolean(user, false);
  }

  static boolean validPassword(String user, String pass){
    return pass.equals(pref.getString(user + "password", ""));
  }

  static void setCostume(String col){
    editor.putString(userInfo + "color", col);
    color = col;
    game1.setCostume(color);
    maze.setCostume(color);
    game3.setCostume(color);
    editor.apply();
  }

  static String getCostume(){
    return pref.getString(userInfo + "color", "blue");
  }

  static String getUserName() {
    return userName;
  }

  static void changeUser() {
    login.resetUser();
  }
}
