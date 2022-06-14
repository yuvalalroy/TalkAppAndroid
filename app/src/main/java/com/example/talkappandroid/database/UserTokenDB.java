package com.example.talkappandroid.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.api.UserServiceAPI;
import com.example.talkappandroid.model.UserItem;
import com.google.gson.Gson;

public class UserTokenDB {
    private static SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static UserTokenDB instance;
    private static String token;

    private UserTokenDB(){
        Context context = TalkAppApplication.context;
        pref = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }

    public static UserTokenDB getInstance(){
        if(instance == null)
            instance = new UserTokenDB();
        return instance;
    }

    public void insertToEditor(UserItem user, String token) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(token, json);
        editor.commit();
    }

    public static UserItem getFromEditor(String token) {
        if(token != null) {
            Gson gson = new Gson();
            String json = pref.getString(token, "");
            return gson.fromJson(json, UserItem.class);
        }
        return null;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserTokenDB.token = token;
    }
}
