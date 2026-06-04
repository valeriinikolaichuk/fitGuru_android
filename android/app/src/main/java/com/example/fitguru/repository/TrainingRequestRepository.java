package com.example.fitguru.repository;

import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.network.ApiService;

import java.util.List;

import retrofit2.Callback;

public class TrainingRequestRepository {

    private final ApiService api;

    public TrainingRequestRepository(ApiService api) {
        this.api = api;
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
