package com.fitguru.backend.program.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.fitguru.backend.program.dto.ProgramDayCreateRequest;
import com.fitguru.backend.program.dto.ProgramDayResponse;
import com.fitguru.backend.program.service.ProgramDayService;

@RestController
@RequestMapping("/api/program-days")
@RequiredArgsConstructor
public class ProgramDayController {

    private final ProgramDayService dayService;

    @PostMapping
    public void createDays(
            @RequestBody List<ProgramDayCreateRequest> requests
    ) {
        dayService.create(requests);
    }

    @GetMapping("/week/{weekId}")
    public List<ProgramDayResponse> getByWeek(
            @PathVariable Long weekId
    ) {
        return dayService.getByWeek(weekId);
    }

    @DeleteMapping("/{dayId}")
    public void delete(
            @PathVariable Long weekId
    ) {
        dayService.delete(weekId);
    }
}
