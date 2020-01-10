package com.example.twittersearch.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {

    private static final String TOKEN_KEY = "token_key";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesManager(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
    }

    public void saveToken(String token) {
        editor.putString(TOKEN_KEY, token).apply();
    }

    public String fetchTokenFromPrefs() {
        return sharedPreferences.getString(TOKEN_KEY, "");
    }
}
