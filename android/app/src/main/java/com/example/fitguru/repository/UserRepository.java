package com.example.fitguru.repository;

import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.trainer.dto.ClientResponse;
import com.example.fitguru.trainer.dto.TrainerResponse;

import java.util.List;

import retrofit2.Callback;

public class UserRepository {

    private final ApiService api;

    public UserRepository(ApiService api) {
        this.api = api;
    }

    public void getClients(
            String token,
            Callback<List<ClientResponse>> callback
    ) {

        api.getClients(token).enqueue(callback);
    }

    public void getTrainers(
            String token,
            Callback<List<TrainerResponse>> callback
    ) {

        api.getTrainers(token).enqueue(callback);
    }

    public void getRequests(
            String token,
            Callback<List<TrainingRequestResponse>> callback
    ) {

        api.getRequests(token).enqueue(callback);
    }

    public void acceptRequest(
            String token,
            Long requestId,
            Callback<Void> callback
    ) {

        api.acceptRequest(token, requestId)
                .enqueue(callback);
    }

    public void rejectRequest(
            String token,
            Long requestId,
            Callback<Void> callback
    ) {

        api.rejectRequest(token, requestId)
                .enqueue(callback);
    }
}
