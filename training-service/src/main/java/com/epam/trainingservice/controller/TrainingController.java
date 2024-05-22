package com.epam.trainingservice.controller;

import com.epam.trainingservice.config.LogEntryExit;
import com.epam.trainingservice.dto.TrainingInfoRequest;
import com.epam.trainingservice.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
@RestController
@RequestMapping("/v1/training")
public class TrainingController {
    private final TrainingService trainingService;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/save")
    @LogEntryExit(showArgs = true, showResult = true)
    public ResponseEntity<HttpStatus> saveInfo(@RequestBody TrainingInfoRequest request){
        logger.info("I am in training-service");
        return trainingService.saveInfo(request);
    }
}
