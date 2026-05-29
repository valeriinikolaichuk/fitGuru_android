package com.example.fitguru.program;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import android.widget.Button;

public class CreateProgramActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_program);

            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> finish());
        }
}

