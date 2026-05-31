package com.fitguru.backend.program.entity;

import java.util.List;
import java.util.ArrayList;

import com.fitguru.backend.program.entity.enums.TrainingDay;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "program_day",
    indexes = {
        @Index(
            name = "idx_program_day_week_position",
            columnList = "program_week_id, position"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_week_id", nullable = false)
    private ProgramWeek week;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingDay day;

    private Integer position;

    @Builder.Default
    @OneToMany(
        mappedBy = "day", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    private List<ProgramExercise> exercises = new ArrayList<>();
}
