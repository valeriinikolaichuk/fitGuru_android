package com.example.fitguru.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.ExerciseViewAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramExerciseResponse;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramDayViewActivity extends AppCompatActivity {

    private Long dayId;

    private TextView tvDayTitle;
    private Button btnBack;

    private RecyclerView rvExercises;

    private ArrayList<ProgramExerciseResponse> exercises;
    private ExerciseViewAdapter adapter;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_day_view);

        dayId = getIntent().getLongExtra("dayId", -1);

        String dayName =
                getIntent().getStringExtra("dayName");

        tvDayTitle = findViewById(R.id.tvDayTitle);
        btnBack = findViewById(R.id.btnBack);
        rvExercises = findViewById(R.id.rvExercises);

        tvDayTitle.setText(dayName);

        sessionManager = new SessionManager(this);

        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        exercises = new ArrayList<>();

        adapter = new ExerciseViewAdapter(exercises);

        rvExercises.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvExercises.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        loadExercises();
    }

    private void loadExercises() {

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
                        exercises.addAll(response.body());

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProgramExerciseResponse>> call,
                            Throwable t
                    ) {

                    }
                }
        );
    }
}
