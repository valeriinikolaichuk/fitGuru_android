package com.fitguru.backend.program.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import com.fitguru.backend.exercise.entity.Exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "program_exercise",
    indexes = {
        @Index(name = "idx_program_day", columnList = "program_day_id"),
        @Index(name = "idx_exercise", columnList = "exercise_id"),
        @Index(name = "idx_program_day_position", columnList = "program_day_id, position")
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
    @JoinColumn(name = "program_day_id", nullable = false)
    private ProgramDay day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Integer position;

    private Double weight;

    private Integer sets;

    private Integer reps;

    private String notes;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
