package com.fitguru.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.trainer.entity.TrainingRequest;
import com.fitguru.backend.user.entity.enums.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phone;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String passwordHash;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime lastLogin;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; 
    
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "trainer")
    private List<TrainerClient> clients;

    @OneToMany(mappedBy = "client")
    private List<TrainerClient> trainers;

    @OneToMany(mappedBy = "trainer")
    private List<TrainingRequest> receivedRequests;

    @OneToMany(mappedBy = "client")
    private List<TrainingRequest> sentRequests;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
