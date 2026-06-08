package com.fitguru.backend.program.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.fitguru.backend.program.service.ProgramExerciseService;
import com.fitguru.backend.program.dto.ProgramExerciseCreateRequest;
import com.fitguru.backend.program.dto.ProgramExerciseResponse;

@RestController
@RequestMapping("/api/program-exercises")
@RequiredArgsConstructor
public class ProgramExerciseController {

    private final ProgramExerciseService exerciseService;

    @PostMapping
    public ProgramExerciseResponse createExercise(
            @RequestBody ProgramExerciseCreateRequest request
    ) {
        return exerciseService.create(request);
    }
}
