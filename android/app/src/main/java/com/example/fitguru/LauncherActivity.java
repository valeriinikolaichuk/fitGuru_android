package com.example.fitguru;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.auth.LoginActivity;
import com.example.fitguru.main.MainActivity;
import com.example.fitguru.storage.SessionManager;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager =
                new SessionManager(this);

//        sessionManager.clear();

        String token = sessionManager.getToken();
        Log.d("TOKEN", "token = " + token);

        if (token != null && !token.isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
    }
}
