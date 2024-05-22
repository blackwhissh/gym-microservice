package com.epam.trainingservice.controller;

import com.epam.trainingservice.config.LogEntryExit;
import com.epam.trainingservice.dto.TrainingInfoRequest;
import com.epam.trainingservice.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/save")
    @LogEntryExit(showArgs = true, showResult = true)
    public ResponseEntity<HttpStatus> saveInfo(@RequestBody TrainingInfoRequest request) {
        return trainingService.saveInfo(request);
    }
}
