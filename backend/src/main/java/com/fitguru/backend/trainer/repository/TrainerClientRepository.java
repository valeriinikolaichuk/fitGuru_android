package com.fitguru.backend.trainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.user.entity.User;

public interface TrainerClientRepository extends JpaRepository<TrainerClient, Long> 
{
    @Query("""
        SELECT tc
        FROM TrainerClient tc
        JOIN FETCH tc.client
        WHERE tc.trainer = :trainer
    """)
    List<TrainerClient> findByTrainer(User trainer);

    @Query("""
        SELECT tc
        FROM TrainerClient tc
        JOIN FETCH tc.trainer
        WHERE tc.client = :client
    """)
    List<TrainerClient> findByClient(User client);
}


