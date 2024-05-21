package com.epam.hibernate.feignClient;

import com.epam.hibernate.dto.TrainingInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("training-service")
public interface TrainingServiceClient {
    @PostMapping("/v1/training/save")
    ResponseEntity<HttpStatus> saveInfo(@RequestBody TrainingInfoRequest request);
}
