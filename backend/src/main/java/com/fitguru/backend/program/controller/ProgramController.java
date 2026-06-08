package com.fitguru.backend.program.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.fitguru.backend.program.service.*;
import com.fitguru.backend.program.dto.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping
    public ProgramResponse ccreateProgram(
            @RequestBody ProgramCreateRequest request
    ) {
        System.out.println("PROGRAM CONTROLLER HIT");
        return programService.create(request);
    }

    @GetMapping("/client/{clientId}")
    public List<ProgramResponse> getProgramsByClient(
            @PathVariable Long clientId
    ) {
        return programService.getProgramsByClient(clientId);
    }

    @PutMapping("/{id}")
    public ProgramResponse updateProgram(
            @PathVariable Long id,
            @RequestBody ProgramUpdateRequest request
    ) {
        return programService.update(id, request);
    }
}