package com.epam.trainingservice.controller;

import com.epam.trainingservice.dto.TrainerSummary;
import com.epam.trainingservice.dto.TrainerSummaryByMonth;
import com.epam.trainingservice.service.WorkloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/workload")
public class WorkloadController {
    private final WorkloadService workloadService;

    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerSummary> getTrainerSummary(@PathVariable String username){
        return workloadService.getTrainerSummary(username);
    }

    @GetMapping("/by-month/{username}")
    public ResponseEntity<TrainerSummaryByMonth> getTrainerSummaryByMonthAndYear(
            @PathVariable String username,
            @RequestParam("year") int year,
            @RequestParam("month") int month){
        return workloadService.getTrainerSummaryByMonthAndYear(username,year,month);
    }

}
