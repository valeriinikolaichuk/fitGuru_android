package com.example.fitguru.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.repository.TrainerClientRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerProgramsActivity extends AppCompatActivity {

    private Long trainerClientId;

    private TrainerClientRepository repository;
    private SessionManager sessionManager;

    private ListView listPrograms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_programs);

        trainerClientId =
                getIntent().getLongExtra(
                        "trainerClientId",
                        -1
                );

        String trainerName =
                getIntent().getStringExtra(
                        "trainerName"
                );

        Button btnBack = findViewById(R.id.btnBack);

        TextView tvTrainerName = findViewById(R.id.tvTrainerName);

        listPrograms = findViewById(R.id.listPrograms);

        tvTrainerName.setText(trainerName);

        sessionManager =
                new SessionManager(this);

        repository =
                new TrainerClientRepository(
                        RetrofitClient
                                .getInstance(sessionManager)
                                .create(ApiService.class)
                );

        btnBack.setOnClickListener(v -> finish());

        loadPrograms();
    }

    private void loadPrograms() {

        if (trainerClientId == null || trainerClientId == -1) {
            return;
        }

        repository.getProgramsByClient(
                trainerClientId,
                new Callback<List<ProgramResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProgramResponse>> call,
                            Response<List<ProgramResponse>> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {
                            return;
                        }

                        List<ProgramResponse> programs =
                                response.body();

                        List<String> titles =
                                new ArrayList<>();

                        for (ProgramResponse p : programs) {
                            titles.add(p.getTitle());
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(
                                        TrainerProgramsActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        titles
                                );

                        listPrograms.setAdapter(adapter);

                        listPrograms.setOnItemClickListener(
                                (parent, view, position, id) -> {

                                    ProgramResponse selected =
                                            programs.get(position);

                                    Intent intent =
                                            new Intent(
                                                    TrainerProgramsActivity.this,
                                                    ProgramViewActivity.class
                                            );

                                    intent.putExtra(
                                            "programId",
                                            selected.getId()
                                    );

                                    intent.putExtra(
                                            "mode",
                                            "CLIENT"
                                    );

                                    startActivity(intent);
                                }
                        );
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProgramResponse>> call,
                            Throwable t
                    ) {
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPrograms();
    }
}
