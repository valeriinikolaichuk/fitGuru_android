package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

public class ProgramExerciseActivity extends AppCompatActivity {

    public long exerciseId;
    public int position;
    private String exerciseName;

    private EditText etWeight;
    private EditText etSets;
    private EditText etReps;
    private EditText etNotes;

    private Button btnSave;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_exercise);

        exerciseId = getIntent().getLongExtra("exerciseId", -1);
        position = getIntent().getIntExtra("position", 0);
        exerciseName = getIntent().getStringExtra("exerciseName");

        etWeight = findViewById(R.id.etWeight);
        etSets = findViewById(R.id.etSets);
        etReps = findViewById(R.id.etReps);
        etNotes = findViewById(R.id.etNotes);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {

            Intent result = new Intent();

            result.putExtra("exerciseId", exerciseId);
            result.putExtra("position", position);
            result.putExtra("exerciseName", exerciseName);

            result.putExtra("weight", Double.parseDouble(etWeight.getText().toString()));
            result.putExtra("sets", Integer.parseInt(etSets.getText().toString()));
            result.putExtra("reps", Integer.parseInt(etReps.getText().toString()));
            result.putExtra("notes", etNotes.getText().toString());

            setResult(RESULT_OK, result);
            finish();
        });
    }
}