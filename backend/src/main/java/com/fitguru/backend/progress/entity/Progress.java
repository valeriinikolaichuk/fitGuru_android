package com.fitguru.backend.progress.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fitguru.backend.program.entity.ProgramExercise;
import com.fitguru.backend.user.entity.User;

@Entity
@Table(name = "progress",    
    indexes = {
        @Index(
            name = "idx_client_exercise",
            columnList = "client_id, program_exercise_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_exercise_id")
    private ProgramExercise programExercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private User client;

    private Boolean doneExercise;

    private Integer doneSets;

    private Integer doneReps;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
