package com.fitguru.backend.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import com.fitguru.backend.user.entity.*;
import com.fitguru.backend.request.entity.TrainingRequest;
import com.fitguru.backend.request.entity.enums.RequestStatus;

public interface TrainingRequestRepository
        extends JpaRepository<TrainingRequest, Long> {

    boolean existsByClientAndTrainerAndStatus(
            User client,
            User trainer,
            RequestStatus status
    );

    Optional<TrainingRequest> findByClientAndTrainerAndStatus(
            User client,
            User trainer,
            RequestStatus status
    );

    List<TrainingRequest> findByClient(User client);

    List<TrainingRequest> findByTrainerAndStatus(
        User trainer,
        RequestStatus status
    );
}
