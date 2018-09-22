package com.tregix.cryptocurrencytracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tregix.cryptocurrencytracker.Model.user.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Confiz123 on 10/11/2017.
 */

public class SharedPreferenceUtil {

    public static final String ISUSERLOGGEDIN = "isUserLoggedIn";
    public static final String USER = "MyObject";
    private final String STORAGE = "com.tregix.cryptocurrency.STORAGE";
    public static final String ISDARKMODEENABLED = "isDarkModeEnabled";
    public static final String SELECTED_THEME = "selectedTheme";
    public static final String ISCARDVIEWENABLED = "isCardViewEnabled";

    private SharedPreferences preferences;
    private Context context;
    private static SharedPreferenceUtil instance;

    public SharedPreferenceUtil(Context context) {
        this.context = context;
    }

    public static SharedPreferenceUtil getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferenceUtil(context);
        }

        return  instance;
    }
    public void storeFavorites(ArrayList<String> arrayList) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("favorites", json);
        editor.apply();
    }

    public ArrayList<String> loadTabs() {
        if(context != null) {
            preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = preferences.getString("favorites", null);
            if (json != null) {
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                return gson.fromJson(json, type);
            }
        }
        return new ArrayList<>();
    }

    public void storeIntValue(String key, int value){
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE, MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key){
        SharedPreferences prefs = context.getSharedPreferences(STORAGE, MODE_PRIVATE);
        return prefs.getInt(key, -1);
    }

    public void storeBooleanValue(String key, boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE, MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolen(String key){
        SharedPreferences prefs = context.getSharedPreferences(STORAGE, MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public boolean isNightMode(){
        SharedPreferences prefs = context.getSharedPreferences(STORAGE, MODE_PRIVATE);
        return prefs.getBoolean(SharedPreferenceUtil.ISDARKMODEENABLED, true);
    }
    public void storeUserObj(User user){
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER, json);
        editor.apply();
    }

    public User getUserObj(){
        SharedPreferences prefs = context.getSharedPreferences(STORAGE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(USER, "");
        return gson.fromJson(json, User.class);
    }

    public void logout(){
        storeBooleanValue(SharedPreferenceUtil.ISUSERLOGGEDIN,false);
        storeUserObj(null);
    }

}
