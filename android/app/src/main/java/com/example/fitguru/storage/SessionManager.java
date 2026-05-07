package com.example.fitguru.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("app", Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        prefs.edit().putString("token", token).apply();
    }

    public String getToken() {
        return prefs.getString("token", null);
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
