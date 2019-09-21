package com.example.kodokode;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private final static int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGGED_IN";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String username, String password) {
        editor.putBoolean(LOGIN, true); //true is default value
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    // checks if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }

//    public User getUser() {
//        String username = sharedPreferences.getString(USERNAME, null);
//        String password = sharedPreferences.getString(PASSWORD, null);
//       // User user = new User(username, password);
//        return user;
//    }

}


