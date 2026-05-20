package com.example.fitguru.main;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.adapters.UserAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.repository.UserRepository;
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
    UserRepository repository;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_list);

        listView = findViewById(R.id.listTrainers);

        SessionManager sessionManager =
                new SessionManager(this);

        api = RetrofitClient
                .getInstance(sessionManager)
                .create(ApiService.class);

        repository = new UserRepository(api);

        loadTrainers();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            TrainerResponse trainer =
                    (TrainerResponse) parent.getItemAtPosition(position);

            sendRequest(trainer.id);
        });
    }

    private void loadTrainers() {

        String token = sessionManager.getAccessToken();

        repository.getTrainers(
                "Bearer " + token,
                new Callback<List<TrainerResponse>>() {

                    @Override
                    public void onResponse(Call<List<TrainerResponse>> call,
                                           Response<List<TrainerResponse>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            UserAdapter adapter =
                                    new UserAdapter(TrainersListActivity.this,
                                            new ArrayList<>(response.body()));

                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TrainerResponse>> call,
                                          Throwable t) {
                    }
                });
    }

    private void sendRequest(Long trainerId) {

        String token = sessionManager.getAccessToken();

        SessionManager sessionManager =
                new SessionManager(this);

        api = RetrofitClient
                .getInstance(sessionManager)
                .create(ApiService.class);

        api.sendRequest("Bearer " + token, trainerId)
                .enqueue(new Callback<Void>() {

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
                });
    }
}
