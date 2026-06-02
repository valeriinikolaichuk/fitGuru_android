package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

import java.util.ArrayList;

public class ProgramDayActivity extends AppCompatActivity {
    private ArrayList<String> exercises;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_day);

        TextView tvDayTitle = findViewById(R.id.tvDayTitle);
        EditText etExercise = findViewById(R.id.etExercise);
        Button btnAddExercise = findViewById(R.id.btnAddExercise);
        ListView listExercises = findViewById(R.id.listExercises);

        String dayName = getIntent().getStringExtra("dayName");
        tvDayTitle.setText(dayName);

        exercises = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                exercises
        );

        listExercises.setAdapter(adapter);

        btnAddExercise.setOnClickListener(v -> {

            String exercise = etExercise.getText().toString().trim();

            if (!exercise.isEmpty()) {
                exercises.add(exercise);
                adapter.notifyDataSetChanged();
                etExercise.setText("");
            }
        });

        listExercises.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent =
                    new Intent(this, ProgramExerciseActivity.class);

            intent.putExtra(
                    "exerciseName",
                    exercises.get(position)
            );

            startActivity(intent);
        });
    }
}
