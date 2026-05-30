package com.fitguru.backend.program_exercise.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import com.fitguru.backend.program.entity.Program;
import com.fitguru.backend.program_exercise.entity.enums.*;
import com.fitguru.backend.exercise.entity.Exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "program_exercise",
    indexes = {
        @Index(name = "idx_program", columnList = "program_id"),
        @Index(name = "idx_exercise", columnList = "exercise_id"),
        @Index(name = "idx_program_day", columnList = "program_id, day")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramExercise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingDay day;

    private String week;

    private Integer position;

    private Double weight;

    private Integer sets;

    private Integer reps;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
