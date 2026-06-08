package com.example.fitguru.repository;

import com.example.fitguru.client.dto.AvailableTrainerResponse;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.main.dto.ClientResponse;
import com.example.fitguru.main.dto.TrainerResponse;
import com.example.fitguru.program.dto.ProgramResponse;

import java.util.List;

import retrofit2.Callback;

public class TrainerClientRepository {

    private final ApiService api;

    public TrainerClientRepository(ApiService api) {
        this.api = api;
    }

    public void getClients(
            Callback<List<ClientResponse>> callback
    ) {
        api.getClients().enqueue(callback);
    }

    public void getTrainers(
            Callback<List<TrainerResponse>> callback
    ) {
        api.getTrainers().enqueue(callback);
    }

    public void getAvailableTrainers(
            Callback<List<AvailableTrainerResponse>> callback
    ) {
        api.getAvailableTrainers().enqueue(callback);
    }

    public void getProgramsByClient(
            Long clientId,
            Callback<List<ProgramResponse>> callback
    ) {
        api.getProgramsByClient(clientId).enqueue(callback);
    }
}
