package com.epam.hibernate.service;

import com.epam.hibernate.dto.AddTrainingRequest;
import com.epam.hibernate.dto.TrainingInfoRequest;
import com.epam.hibernate.entity.Trainee;
import com.epam.hibernate.entity.Trainer;
import com.epam.hibernate.entity.Training;
import com.epam.hibernate.entity.TrainingType;
import com.epam.hibernate.exception.TrainingNotFoundException;
import com.epam.hibernate.feignClient.TrainingServiceClient;
import com.epam.hibernate.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;

@Service
public class TrainingService {
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingServiceClient trainingServiceClient;
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingService(TrainerRepository trainerRepository,
                           TraineeRepository traineeRepository,
                           TrainingTypeRepository trainingTypeRepository,
                           TrainingServiceClient trainingServiceClient, TrainingRepository trainingRepository) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingServiceClient = trainingServiceClient;
        this.trainingRepository = trainingRepository;
    }

    @Transactional
    public ResponseEntity<?> addTraining(@NotNull AddTrainingRequest request) throws NotActiveException {
        Trainee trainee = traineeRepository.selectByUsername(request.getTraineeUsername());
        Trainer trainer = trainerRepository.selectByUsername(request.getTrainerUsername());
        if (!trainer.getUser().getActive() || !trainee.getUser().getActive()) {
            throw new NotActiveException("Trainer/Trainee is not active");
        }

        TrainingType trainingType = trainingTypeRepository.selectByType(request.getTrainingType());
        if (trainer.getSpecialization().getTrainingTypeName() != trainingType.getTrainingTypeName()) {
            throw new IllegalArgumentException("Trainer has not that specialization");
        }

        Training training = new Training(trainer, trainee, request.getTrainingName(),
                trainingType, request.getTrainingDate(), request.getDuration());

        trainer.getTrainings().add(training);
        trainer.getTrainees().add(trainee);

        trainee.getTrainings().add(training);
        trainee.getTrainers().add(trainer);

        trainerRepository.save(trainer);
        traineeRepository.save(trainee);

        trainingServiceClient.saveInfo(new TrainingInfoRequest(
                trainer.getUser().getUsername(),
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getUser().getActive(),
                training.getTrainingDate(),
                training.getTrainingDuration(),
                "ADD"));
        return ResponseEntity.status(200).body("Training added successfully");
    }

    public ResponseEntity<List<TrainingType>> getTrainingTypes() {
        return ResponseEntity.ok().body(trainingTypeRepository.getAll());
    }

    public ResponseEntity<?> removeTraining(Long trainingId) {
        Training training = trainingRepository.findById(trainingId);
        if(training == null){
            throw new TrainingNotFoundException();
        }
        trainingRepository.delete(trainingId);
        trainingServiceClient.saveInfo(new TrainingInfoRequest(
                training.getTrainer().getUser().getUsername(),
                training.getTrainer().getUser().getFirstName(),
                training.getTrainer().getUser().getLastName(),
                training.getTrainer().getUser().getActive(),
                training.getTrainingDate(),
                training.getTrainingDuration(),
                "CANCEL"));
        return ResponseEntity.ok("Training removed successfully");
    }
}
