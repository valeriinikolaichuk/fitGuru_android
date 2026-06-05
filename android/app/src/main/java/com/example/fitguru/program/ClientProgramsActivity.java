package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramCreateRequest;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientProgramsActivity extends AppCompatActivity {

    Long clientId;
    private ProgramCreateRepository repository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_programs);

        clientId = getIntent().getLongExtra("clientId", -1);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnCreate = findViewById(R.id.btnCreateProgram);

        sessionManager = new SessionManager(this);

        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        btnCreate.setOnClickListener(v -> {

            ProgramCreateRequest request = new ProgramCreateRequest();
            request.trainerClientId = clientId;
            request.title = "";

            repository.createProgram(
                    request,
                    new Callback<ProgramResponse>() {

                        @Override
                        public void onResponse(
                                Call<ProgramResponse> call,
                                Response<ProgramResponse> response
                        ) {

                            if (!response.isSuccessful() || response.body() == null) {
                                return;
                            }

                            Long programId = response.body().getId();

                            Intent intent = new Intent(
                                    ClientProgramsActivity.this,
                                    CreateProgramActivity.class
                            );

                            intent.putExtra("programId", programId);
                            intent.putExtra("clientId", clientId);

                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(
                                Call<ProgramResponse> call,
                                Throwable t
                        ) {
                            t.printStackTrace();
                        }
                    }
            );
        });

        TextView tvClientName = findViewById(R.id.tvClientName);
        String clientName = getIntent().getStringExtra("clientName");
        tvClientName.setText(clientName);

        loadPrograms(clientId);
    }

    private void loadPrograms(Long clientId) {
        // поки заглушка
        System.out.println("Load programs for client: " + clientId);
    }
}
