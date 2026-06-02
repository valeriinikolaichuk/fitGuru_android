package com.fitguru.backend.program.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping
    public ProgramResponse create(
            @RequestBody ProgramCreateRequest request) {

        return programService.create(request);
    }
}