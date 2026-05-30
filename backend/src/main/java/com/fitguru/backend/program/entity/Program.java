package com.fitguru.backend.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import com.fitguru.backend.trainer.entity.TrainerClient;
import com.fitguru.backend.program.entity.enums.ProgramStatus;
import com.fitguru.backend.program_exercise.entity.ProgramExercise;

@Entity
@Table(
    name = "program",
    indexes = {
        @Index(name = "idx_trainer_client", columnList = "trainer_client_id"),
        @Index(name = "idx_status", columnList = "status")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_client_id", nullable = false)
    private TrainerClient trainerClient;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgramStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = ProgramStatus.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder.Default
    @OneToMany(
        mappedBy = "program",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    
    private List<ProgramExercise> exercises = new ArrayList<>();
}
