package com.example.fitguru.repository;

import com.example.fitguru.network.ApiService;

public class TrainingRequestRepository {

    private final ApiService api;

    public TrainingRequestRepository(ApiService api) {
        this.api = api;
    }

}
