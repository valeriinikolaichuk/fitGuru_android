package com.example.fitguru.client;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.adapters.TrainerAdapter;
import com.example.fitguru.client.dto.AvailableTrainerResponse;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.repository.TrainerClientRepository;
import com.example.fitguru.repository.TrainingRequestRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainersListActivity extends AppCompatActivity {

    ListView listView;
    ApiService api;
    TrainerClientRepository repository;
    TrainingRequestRepository repositoryRequest;
    SessionManager sessionManager;
    TrainerAdapter adapter;
    List<AvailableTrainerResponse> trainers;

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

        findViewById(R.id.btnBackTo)
                .setOnClickListener(v -> finish());
    }

    private void loadTrainers() {

        repository.getAvailableTrainers(
                new Callback<List<AvailableTrainerResponse>>() {

                    @Override
                    public void onResponse(Call<List<AvailableTrainerResponse>> call,
                                           Response<List<AvailableTrainerResponse>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            trainers = new ArrayList<>(response.body());

                            adapter = new TrainerAdapter(
                                    TrainersListActivity.this,
                                    trainers,
                                    new TrainerAdapter.OnTrainerClickListener() {

                                        @Override
                                        public void onSendRequest(AvailableTrainerResponse trainer, int position) {

                                            sendRequest(trainer, position, true);
                                        }

                                        @Override
                                        public void onCancelRequest(AvailableTrainerResponse trainer, int position) {

                                            cancelRequest(trainer, position);
                                        }
                                    }
                            );

                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AvailableTrainerResponse>> call,
                                          Throwable t) {
                        Toast.makeText(
                                TrainersListActivity.this,
                                "Error loading trainers",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void sendRequest(AvailableTrainerResponse trainer,
                             int position,
                             boolean isSend) {

        repositoryRequest.sendRequest(
                trainer.getId(),
                new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {

                            trainer.setRequestStatus("PENDING");

                            adapter.notifyDataSetChanged();
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

    private void cancelRequest(AvailableTrainerResponse trainer,
                               int position) {

        repositoryRequest.cancelRequest(
                trainer.getId(),
                new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {

                        if (response.isSuccessful()) {

                            trainer.setRequestStatus("NONE");

                            adapter.notifyDataSetChanged();
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
