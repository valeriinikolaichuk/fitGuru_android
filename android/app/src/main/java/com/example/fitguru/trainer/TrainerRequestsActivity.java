package com.example.fitguru.trainer;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;
import com.example.fitguru.adapters.RequestAdapter;
import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.repository.TrainerClientRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerRequestsActivity extends AppCompatActivity {

    ListView listView;
    ApiService api;
    TrainerClientRepository repository;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        listView = findViewById(R.id.listRequests);

        SessionManager sessionManager =
                new SessionManager(this);

        api = RetrofitClient
                .getInstance(sessionManager)
                .create(ApiService.class);

        repository = new TrainerClientRepository(api);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        loadRequests();
    }

    private void loadRequests() {

        repository.getRequests(
                new Callback<List<TrainingRequestResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<TrainingRequestResponse>> call,
                            Response<List<TrainingRequestResponse>> response
                    ) {

                        if (response.isSuccessful()) {

                            RequestAdapter adapter =
                                    new RequestAdapter(
                                            TrainerRequestsActivity.this,
                                            response.body(),
                                            repository
                                    );

                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<TrainingRequestResponse>> call,
                            Throwable t
                    ) {

                    }
                }
        );
    }
}
