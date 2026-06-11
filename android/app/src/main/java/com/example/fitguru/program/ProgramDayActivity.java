package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    private Button btnAddExercise, btnBack, btnSave, btnDeleteDay;
    private RecyclerView rvExercises;
    private TextView tvDayTitle;
    private Long dayId;
    private ExerciseResponse selectedExercise;

    private List<MuscleGroup> muscleGroups;
    private List<ExerciseResponse> availableExercises;

    private ArrayList<ProgramExerciseCreateRequest> exercises;
    private ExercisesAdapter adapter;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    private ActivityResultLauncher<Intent> exerciseLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DAY_ACTIVITY", "START");
        setContentView(R.layout.activity_program_day);

        initViews();
        initData();
        initRecycler();
        loadDayExercises();
        initSpinners();
        initLauncher();
        initListeners();
    }

    // ---------------- INIT VIEWS ----------------
    private void initViews() {
        tvDayTitle = findViewById(R.id.tvDayTitle);

        spinnerMuscleGroup = findViewById(R.id.spinnerMuscleGroup);
        spinnerExercise = findViewById(R.id.spinnerExercise);

        btnAddExercise = findViewById(R.id.btnAddExercise);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnDeleteDay = findViewById(R.id.btnDeleteDay);

        rvExercises = findViewById(R.id.rvExercises);
    }

    // ---------------- INIT DATA ----------------
    private void initData() {

        dayId = getIntent().getLongExtra("dayId", -1);
        tvDayTitle.setText(getIntent().getStringExtra("dayName"));

        sessionManager = new SessionManager(this);
        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        muscleGroups = Arrays.asList(MuscleGroup.values());
        availableExercises = new ArrayList<>();
        exercises = new ArrayList<>();
    }

    // ---------------- INIT RECYCLER ----------------
    private void initRecycler() {

        adapter = new ExercisesAdapter(exercises, position -> {

            ProgramExerciseCreateRequest item = exercises.get(position);

            Intent intent = new Intent(
                    this,
                    ProgramExerciseActivity.class
            );

            intent.putExtra("exerciseId", item.exerciseId);
            intent.putExtra("position", item.position);

            exerciseLauncher.launch(intent);
        });

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(adapter);
    }

    // ---------------- INIT SPINNERS ----------------
    private void initSpinners() {

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
    }

    // ---------------- ACTIVITY RESULT API ----------------
    private void initLauncher() {

        exerciseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() != RESULT_OK || result.getData() == null) {
                        return;
                    }

                    Intent data = result.getData();

                    ProgramExerciseCreateRequest req = new ProgramExerciseCreateRequest();

                    req.dayId = dayId;

                    req.exerciseId = data.getLongExtra("exerciseId", -1);
                    req.position = data.getIntExtra("position", exercises.size());
                    req.exerciseName = data.getStringExtra("exerciseName");

                    req.weight = data.getDoubleExtra("weight", 0);
                    req.sets = data.getIntExtra("sets", 0);
                    req.reps = data.getIntExtra("reps", 0);
                    req.notes = data.getStringExtra("notes");

                    exercises.add(req);
                    adapter.notifyItemInserted(exercises.size() - 1);
                }
        );
    }

    // ---------------- BUTTONS ----------------
    private void initListeners() {

        btnBack.setOnClickListener(v -> finish());

        btnAddExercise.setOnClickListener(v -> {

            if (availableExercises.isEmpty()) return;

            selectedExercise =
                    (ExerciseResponse) spinnerExercise.getSelectedItem();

            Intent intent = new Intent(this, ProgramExerciseActivity.class);

            intent.putExtra("exerciseId", selectedExercise.id);
            intent.putExtra("exerciseName", selectedExercise.exercise);
            intent.putExtra("position", exercises.size());

            exerciseLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> saveExercises());





        btnDeleteDay.setOnClickListener(v -> {

            repository.deleteDay(
                    dayId,
                    new Callback<Void>() {

                        @Override
                        public void onResponse(
                                Call<Void> call,
                                Response<Void> response
                        ) {

                            if (response.isSuccessful()) {

                                Toast.makeText(
                                        ProgramDayActivity.this,
                                        "Day deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<Void> call,
                                Throwable t
                        ) {
                            Toast.makeText(
                                    ProgramDayActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
        });




    }

    // ---------------- SAVE ----------------
    private void saveExercises() {

        if (exercises.isEmpty()) {
            Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ProgramExerciseCreateRequest req : exercises) {
            if (req.id != null) {
                continue;
            }

            repository.createExercise(
                    req,
                    new Callback<ProgramExerciseResponse>() {

                        @Override
                        public void onResponse(
                                Call<ProgramExerciseResponse> call,
                                Response<ProgramExerciseResponse> response
                        ) {

                            if (!response.isSuccessful()
                                    || response.body() == null) {

                                Log.e(
                                        "PROGRAM",
                                        "Save failed: " + response.code()
                                );
                                return;
                            }

                            req.id = response.body().getId();

                            Log.d(
                                    "PROGRAM",
                                    "Exercise saved id = " + req.id
                            );
                        }

                        @Override
                        public void onFailure(
                                Call<ProgramExerciseResponse> call,
                                Throwable t
                        ) {
                            Log.e(
                                    "PROGRAM",
                                    "Exercise save error",
                                    t
                            );
                        }
                    }
            );
        }

        Toast.makeText(
                this,
                "Save started",
                Toast.LENGTH_SHORT
        ).show();
    }

    // ---------------- LOAD EXERCISES ----------------
    private void loadExercises(MuscleGroup muscleGroup) {

        repository.getExercisesByGroup(
                muscleGroup.name(),
                new Callback<List<ExerciseResponse>>() {

                    @Override
                    public void onResponse(Call<List<ExerciseResponse>> call,
                                           Response<List<ExerciseResponse>> response) {

                        if (!response.isSuccessful() || response.body() == null) return;

                        availableExercises = response.body();

                        ArrayAdapter<ExerciseResponse> adapter =
                                new ArrayAdapter<>(
                                        ProgramDayActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        availableExercises
                                );

                        spinnerExercise.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<ExerciseResponse>> call, Throwable t) {
                        Log.e("PROGRAM", "Load exercises error", t);
                    }
                }
        );
    }

    private void loadDayExercises() {

        repository.getExercisesByDay(
                dayId,
                new Callback<List<ProgramExerciseResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProgramExerciseResponse>> call,
                            Response<List<ProgramExerciseResponse>> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {
                            return;
                        }

                        exercises.clear();

                        for (ProgramExerciseResponse item : response.body()) {

                            ProgramExerciseCreateRequest req =
                                    new ProgramExerciseCreateRequest();

                            req.id = item.getId();
                            req.exerciseId = item.getExerciseId();
                            req.exerciseName = item.getExerciseName();
                            req.position = item.getPosition();

                            req.weight = item.getWeight();
                            req.sets = item.getSets();
                            req.reps = item.getReps();
                            req.notes = item.getNotes();

                            exercises.add(req);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProgramExerciseResponse>> call,
                            Throwable t
                    ) {
                        Log.e("PROGRAM", "Load exercises error", t);
                    }
                }
        );
    }
}
