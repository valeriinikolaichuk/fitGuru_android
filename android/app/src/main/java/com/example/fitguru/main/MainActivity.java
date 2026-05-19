package com.example.fitguru.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

import com.example.fitguru.adapters.UserAdapter;
import com.example.fitguru.auth.LoginActivity;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.ProgramsActivity;
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
    Button btnRequests;
    Button btnFindTrainers;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        api = RetrofitClient.getInstance().create(ApiService.class);
        repository = new UserRepository(api);
        sessionManager = new SessionManager(this);
        listView = findViewById(R.id.listClients);
        btnRequests = findViewById(R.id.btnRequests);
        btnFindTrainers = findViewById(R.id.btnFindTrainers);
        btnLogout = findViewById(R.id.btnLogout);

        listView.setOnItemClickListener((
                parent,
                view,
                position,
                id) -> {

            Object item = parent.getItemAtPosition(position);
            String role = sessionManager.getRole();

            if ("CLIENT".equals(role) && item instanceof TrainerResponse) {
                TrainerResponse trainer = (TrainerResponse) item;
                sendRequest(trainer.id);

            } else {
                Intent intent = new Intent(MainActivity.this, ProgramsActivity.class);

                if (item instanceof ClientResponse) {
                    intent.putExtra("userId", ((ClientResponse) item).id);
                } else if (item instanceof TrainerResponse) {
                    intent.putExtra("userId", ((TrainerResponse) item).id);
                }

                startActivity(intent);
            }
        });

        String role = sessionManager.getRole();

        btnLogout.setOnClickListener(v -> {

            SessionManager sessionManager =
                    new SessionManager(MainActivity.this);

            sessionManager.clear();

            startActivity(new Intent(
                    MainActivity.this,
                    LoginActivity.class
            ));

            finish();
        });

        if ("TRAINER".equals(role)) {

            btnRequests.setVisibility(View.VISIBLE);

            btnRequests.setOnClickListener(v -> {
                startActivity(
                        new Intent(MainActivity.this,
                                RequestsActivity.class)
                );
            });

//          Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();

            loadClients();
        } else {
            btnFindTrainers.setVisibility(View.VISIBLE);

            btnFindTrainers.setOnClickListener(v -> {
                startActivity(new Intent(
                        MainActivity.this,
                        TrainersListActivity.class
                ));
            });

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

                            List<Object> items = new ArrayList<>();

                            for (ClientResponse c : response.body()) {
                                items.add(c);
                            }

                            UserAdapter adapter = new UserAdapter(MainActivity.this, items);
                            listView.setAdapter(adapter);
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

                            List<Object> items = new ArrayList<>();

                            for (TrainerResponse c : response.body()) {
                                items.add(c);
                            }

                            UserAdapter adapter = new UserAdapter(MainActivity.this, items);
                            listView.setAdapter(adapter);
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

    private void sendRequest(Long trainerId) {

        String token = sessionManager.getToken();

        repository.sendRequest(
                "Bearer " + token,
                trainerId,
                new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response
                    ) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Request sent",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                MainActivity.this,
                                "Error",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}
