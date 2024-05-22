package com.epam.trainingservice.controller;

import com.epam.trainingservice.config.LogEntryExit;
import com.epam.trainingservice.dto.TrainerSummary;
import com.epam.trainingservice.dto.TrainerSummaryByMonth;
import com.epam.trainingservice.service.WorkloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/workload")
@CrossOrigin(origins = "*")
public class WorkloadController {
    private final WorkloadService workloadService;

    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @LogEntryExit(showArgs = true, showResult = true)
    public ResponseEntity<TrainerSummary> getTrainerSummary(@PathVariable String username) {
        return workloadService.getTrainerSummary(username);
    }

    @GetMapping("/by-month/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @LogEntryExit(showArgs = true, showResult = true)
    public ResponseEntity<TrainerSummaryByMonth> getTrainerSummaryByMonthAndYear(
            @PathVariable String username,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return workloadService.getTrainerSummaryByMonthAndYear(username, year, month);
    }

}
