package com.example.fitguru.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.example.fitguru.R;
import com.example.fitguru.auth.RegisterActivity;

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