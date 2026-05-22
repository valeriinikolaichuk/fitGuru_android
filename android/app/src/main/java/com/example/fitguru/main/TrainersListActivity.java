package com.example.fitguru.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.adapters.TrainerAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.repository.TrainerClientRepository;
import com.example.fitguru.storage.SessionManager;
import com.example.fitguru.trainer.dto.TrainerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainersListActivity extends AppCompatActivity {

    ListView listView;
    ApiService api;
    TrainerClientRepository repository;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_list);

        listView = findViewById(R.id.listTrainers);

        sessionManager = new SessionManager(this);

        api = RetrofitClient
                .getInstance(sessionManager)
                .create(ApiService.class);

        repository = new TrainerClientRepository(api);

        loadTrainers();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            TrainerResponse trainer =
                    (TrainerResponse) parent.getItemAtPosition(position);

            sendRequest(trainer.id);
        });

        Button btnBack = findViewById(R.id.btnBackTo);

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadTrainers() {

        String token = sessionManager.getAccessToken();

        repository.getAvailableTrainers(
                new Callback<List<TrainerResponse>>() {

                    @Override
                    public void onResponse(Call<List<TrainerResponse>> call,
                                           Response<List<TrainerResponse>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            TrainerAdapter adapter = new TrainerAdapter(
                                    TrainersListActivity.this,
                                    new ArrayList<>(response.body()),
                                    trainer -> sendRequest(trainer.getId())
                            );

                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TrainerResponse>> call,
                                          Throwable t) {
                        Toast.makeText(
                                TrainersListActivity.this,
                                "Error loading trainers",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void sendRequest(Long trainerId) {
        Log.e("API", "SEND REQUEST CALLED: " + trainerId);
        String token = sessionManager.getAccessToken();

        repository.sendRequest(
                trainerId,
                new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(
                                    TrainersListActivity.this,
                                    "Request sent",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(
                                TrainersListActivity.this,
                                "Error",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}
