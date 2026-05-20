package com.example.fitguru.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(
                "app",
                Context.MODE_PRIVATE
        );
    }

    public void saveAccessToken(String token) {
    prefs.edit().putString("accessToken", token).apply();
}

    public void saveRefreshToken(String token) {
        prefs.edit().putString("refreshToken", token).apply();
    }

    public String getAccessToken() {
        return prefs.getString("accessToken", null);
    }

    public String getRefreshToken() {
        return prefs.getString("refreshToken", null);
    }
    public void saveRole(String role) {
        prefs.edit().putString("role", role).apply();
    }

    public String getRole() {
        return prefs.getString("role", null);
    }

    public void clear() {
        prefs.edit()
                .clear()
                .apply();
    }
}
