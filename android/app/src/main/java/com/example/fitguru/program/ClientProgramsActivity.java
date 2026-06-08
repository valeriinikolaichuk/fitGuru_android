package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramCreateRequest;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.repository.TrainerClientRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientProgramsActivity extends AppCompatActivity {

    private Long trainerClientId;

    private ProgramCreateRepository repository;
    private TrainerClientRepository repositoryPrograms;
    private SessionManager sessionManager;

    private ListView listPrograms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_programs);

        trainerClientId = getIntent().getLongExtra("trainerClientId", -1);
        Log.d("PROGRAM", "trainerClientId = " + trainerClientId);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnCreate = findViewById(R.id.btnCreateProgram);

        TextView tvClientName = findViewById(R.id.tvClientName);
        listPrograms = findViewById(R.id.listPrograms);

        String clientName = getIntent().getStringExtra("clientName");
        tvClientName.setText(clientName != null ? clientName : "");

        sessionManager = new SessionManager(this);

        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        repositoryPrograms = new TrainerClientRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        btnBack.setOnClickListener(v -> finish());

        btnCreate.setOnClickListener(v -> createProgram());

        loadPrograms(trainerClientId);
    }

    private void createProgram() {

        Log.d("PROGRAM", "Button clicked");

        if (trainerClientId == null || trainerClientId == -1) {
            Log.e("PROGRAM", "Invalid trainerClientId");
            return;
        }

        ProgramCreateRequest request =
                new ProgramCreateRequest();

        request.trainerClientId = trainerClientId;
        request.title = "New Program";

        repository.createProgram(
                request,
                new Callback<ProgramResponse>() {

                    @Override
                    public void onResponse(
                            Call<ProgramResponse> call,
                            Response<ProgramResponse> response
                    ) {

                        Log.d(
                                "PROGRAM",
                                "response code = " + response.code()
                        );

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            Log.e(
                                    "PROGRAM",
                                    "Response failed"
                            );
                            return;
                        }

                        Long programId =
                                response.body().getId();

                        Intent intent =
                                new Intent(
                                        ClientProgramsActivity.this,
                                        CreateProgramActivity.class
                                );

                        intent.putExtra(
                                "programId",
                                programId
                        );

                        intent.putExtra(
                                "trainerClientId",
                                trainerClientId
                        );

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(
                            Call<ProgramResponse> call,
                            Throwable t
                    ) {

                        Log.e(
                                "PROGRAM",
                                "Network error",
                                t
                        );
                    }
                }
        );
    }

    private void loadPrograms(Long trainerClientId) {

        if (trainerClientId == null
                || trainerClientId == -1) {

            Log.e(
                    "PROGRAM",
                    "Invalid trainerClientId"
            );
            return;
        }

        repositoryPrograms.getProgramsByClient(
                trainerClientId,
                new Callback<List<ProgramResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProgramResponse>> call,
                            Response<List<ProgramResponse>> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            Log.e(
                                    "PROGRAM",
                                    "Failed to load programs"
                            );
                            return;
                        }

                        List<ProgramResponse> programs =
                                response.body();

                        Log.d(
                                "PROGRAM",
                                "Loaded programs: "
                                        + programs.size()
                        );

                        List<String> titles =
                                new ArrayList<>();

                        for (ProgramResponse program : programs) {

                            titles.add(
                                    program.getTitle()
                            );
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(
                                        ClientProgramsActivity.this,
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
                                                    ClientProgramsActivity.this,
                                                    CreateProgramActivity.class
                                            );

                                    intent.putExtra(
                                            "programId",
                                            selected.getId()
                                    );

                                    intent.putExtra(
                                            "trainerClientId",
                                            trainerClientId
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

                        Log.e(
                                "PROGRAM",
                                "Error loading programs",
                                t
                        );
                    }
                }
        );
    }
}