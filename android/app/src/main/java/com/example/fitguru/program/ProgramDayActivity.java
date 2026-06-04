package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.ExercisesAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ExerciseResponse;
import com.example.fitguru.program.dto.ProgramExerciseCreateRequest;
import com.example.fitguru.program.dto.ProgramExerciseResponse;
import com.example.fitguru.program.model.enums.MuscleGroup;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramDayActivity extends AppCompatActivity {

    private Spinner spinnerMuscleGroup;
    private Spinner spinnerExercise;

    private Button btnAddExercise, btnBack, btnSave;
    private RecyclerView rvExercises;

    private List<MuscleGroup> muscleGroups;
    private List<ExerciseResponse> availableExercises;

    private ArrayList<ProgramExerciseCreateRequest> exercises;

    private ExercisesAdapter adapter;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    private final int REQUEST_EXERCISE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_day);

        TextView tvDayTitle = findViewById(R.id.tvDayTitle);

        spinnerMuscleGroup = findViewById(R.id.spinnerMuscleGroup);
        spinnerExercise = findViewById(R.id.spinnerExercise);

        btnAddExercise = findViewById(R.id.btnAddExercise);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        rvExercises = findViewById(R.id.rvExercises);

        tvDayTitle.setText(getIntent().getStringExtra("dayName"));

        btnBack.setOnClickListener(v -> finish());

        sessionManager = new SessionManager(this);
        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        muscleGroups = Arrays.asList(MuscleGroup.values());
        availableExercises = new ArrayList<>();
        exercises = new ArrayList<>();

        ArrayAdapter<MuscleGroup> muscleAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        muscleGroups
                );

        spinnerMuscleGroup.setAdapter(muscleAdapter);

        spinnerMuscleGroup.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        loadExercises(muscleGroups.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );

        adapter = new ExercisesAdapter(exercises, position -> {

            ProgramExerciseCreateRequest item = exercises.get(position);

            Intent intent = new Intent(
                    this,
                    ProgramExerciseActivity.class
            );

            intent.putExtra("exerciseId", item.exerciseId);
            intent.putExtra("position", item.position);

            startActivityForResult(intent, REQUEST_EXERCISE);
        });

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(adapter);

        btnAddExercise.setOnClickListener(v -> {

            if (availableExercises.isEmpty()) return;

            ExerciseResponse selected =
                    availableExercises.get(
                            spinnerExercise.getSelectedItemPosition()
                    );

            Intent intent = new Intent(
                    this,
                    ProgramExerciseActivity.class
            );

            intent.putExtra("exerciseId", selected.id);
            intent.putExtra("position", exercises.size());

            startActivityForResult(intent, REQUEST_EXERCISE);
        });

        btnSave.setOnClickListener(v -> {

            for (ProgramExerciseCreateRequest req : exercises) {
                repository.createExercise(req, new Callback<ProgramExerciseResponse>() {
                    @Override
                    public void onResponse(Call<ProgramExerciseResponse> call, Response<ProgramExerciseResponse> response) {}

                    @Override
                    public void onFailure(Call<ProgramExerciseResponse> call, Throwable t) {}
                });
            }

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EXERCISE && resultCode == RESULT_OK) {

            ProgramExerciseCreateRequest req = new ProgramExerciseCreateRequest();

            req.exerciseId = data.getLongExtra("exerciseId", -1);
            req.position = data.getIntExtra("position", 0);

            req.weight = data.getDoubleExtra("weight", 0);
            req.sets = data.getIntExtra("sets", 0);
            req.reps = data.getIntExtra("reps", 0);
            req.notes = data.getStringExtra("notes");

            exercises.add(req);

            adapter.notifyItemInserted(exercises.size() - 1);
        }
    }

    private void loadExercises(MuscleGroup muscleGroup) {

        repository.getExercisesByGroup(
                muscleGroup.name(),
                new Callback<List<ExerciseResponse>>() {

                    @Override
                    public void onResponse(Call<List<ExerciseResponse>> call, Response<List<ExerciseResponse>> response) {

                        if (!response.isSuccessful() || response.body() == null) return;

                        availableExercises = response.body();

                        List<String> names = new ArrayList<>();

                        for (ExerciseResponse e : availableExercises) {
                            names.add(e.exercise);
                        }

                        spinnerExercise.setAdapter(
                                new ArrayAdapter<>(
                                        ProgramDayActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        names
                                )
                        );
                    }

                    @Override
                    public void onFailure(Call<List<ExerciseResponse>> call, Throwable t) {}
                }
        );
    }
}