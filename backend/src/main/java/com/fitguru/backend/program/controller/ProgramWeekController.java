package com.fitguru.backend.program.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitguru.backend.program.dto.ProgramWeekCreateRequest;
import com.fitguru.backend.program.dto.ProgramWeekResponse;
import com.fitguru.backend.program.service.ProgramWeekService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/program-weeks")
@RequiredArgsConstructor
public class ProgramWeekController {

    private final ProgramWeekService weekService;

    @PostMapping
    public ProgramWeekResponse createWeek(
            @RequestBody ProgramWeekCreateRequest request
    ) {
        return weekService.create(request);
    }
}
