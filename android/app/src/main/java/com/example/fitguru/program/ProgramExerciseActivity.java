package com.example.fitguru.program;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

public class ProgramExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_exercise);

        TextView tvExercise = findViewById(R.id.tvExercise);

        String exercise =
                getIntent().getStringExtra("exerciseName");

        tvExercise.setText(exercise);
    }
}
