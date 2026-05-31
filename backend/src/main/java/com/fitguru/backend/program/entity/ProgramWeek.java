package com.fitguru.backend.program.entity;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "program_week",
    indexes = {
        @Index(
            name = "idx_program_week_program_position",
            columnList = "program_id, position"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Builder.Default
    @OneToMany(
        mappedBy = "week", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    private List<ProgramDay> days = new ArrayList<>();

    private String title;

    private Integer position;
}