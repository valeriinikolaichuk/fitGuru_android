package com.fitguru.backend.trainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.user.entity.User;

public interface TrainerClientRepository extends JpaRepository<TrainerClient, Long> 
{
    List<TrainerClient> findByTrainer(User trainer);
}


