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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiService api;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listClients);

        api = RetrofitClient.getInstance().create(ApiService.class);

        SessionManager sessionManager =
                new SessionManager(this);
        String token = sessionManager.getToken();

        api.getClients("Bearer " + token)
                .enqueue(new Callback<List<ClientResponse>>() {
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
}