package com.example.fitguru.repository;

import com.example.fitguru.client.dto.AvailableTrainerResponse;
import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.main.dto.ClientResponse;
import com.example.fitguru.main.dto.TrainerResponse;

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

    public void sendRequest(
            Long trainerId,
            Callback<Void> callback
    ) {
        api.sendRequest(trainerId)
                .enqueue(callback);
    }

    public void cancelRequest(
            Long trainerId,
            Callback<Void> callback
    ) {
        api.cancelRequest(trainerId).enqueue(callback);
    }

    public void getRequests(
            Callback<List<TrainingRequestResponse>> callback
    ) {
        api.getRequests().enqueue(callback);
    }







    public void acceptRequest(
            Long requestId,
            Callback<Void> callback
    ) {
        api.acceptRequest(requestId)
                .enqueue(callback);
    }

    public void rejectRequest(
            Long requestId,
            Callback<Void> callback
    ) {

        api.rejectRequest(requestId)
                .enqueue(callback);
    }
}
