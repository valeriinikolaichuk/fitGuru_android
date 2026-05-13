package com.example.fitguru.main;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.storage.SessionManager;
import com.example.fitguru.trainer.dto.ClientResponse;
import com.example.fitguru.trainer.dto.TrainerResponse;
import com.example.fitguru.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiService api;
UserRepository repository;
    ListView listView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        api = RetrofitClient.getInstance().create(ApiService.class);
        repository = new UserRepository(api);

        sessionManager = new SessionManager(this);

        listView = findViewById(R.id.listClients);

        String role = sessionManager.getRole();

        if (role.equals("TRAINER")) {
            loadClients();
        } else {
            loadTrainers();
        }
    }

    private void loadClients() {

        String token = sessionManager.getToken();

        repository.getClients(
                "Bearer " + token,
                new Callback<List<ClientResponse>>() {

                    @Override
                    public void onResponse(Call<List<ClientResponse>> call,
                                           Response<List<ClientResponse>> response) {

                        if (response.isSuccessful()) {

                            List<String> names = new ArrayList<>();

                            for (ClientResponse c : response.body()) {
                                names.add(c.name + " (" + c.phone + ")");
                            }

                            listView.setAdapter(
                                    new ArrayAdapter<>(
                                            MainActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            names
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClientResponse>> call,
                                          Throwable t) {
                    }
                });
    }

    private void loadTrainers() {

        String token = sessionManager.getToken();

        repository.getTrainers(
                "Bearer " + token,
                new Callback<List<TrainerResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<TrainerResponse>> call,
                            Response<List<TrainerResponse>> response
                    ) {

                        if (response.isSuccessful()) {

                            List<String> names = new ArrayList<>();

                            for (TrainerResponse t : response.body()) {

                                names.add(t.name);
                            }

                            listView.setAdapter(
                                    new ArrayAdapter<>(
                                            MainActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            names
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<TrainerResponse>> call,
                            Throwable t
                    ) {

                    }
                });
    }
}
