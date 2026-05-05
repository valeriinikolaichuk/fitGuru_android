package com.example.fitguru;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isRegistered = getSharedPreferences("app", MODE_PRIVATE)
                .getBoolean("is_registered", false);

        if (!isRegistered) {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
    }
}