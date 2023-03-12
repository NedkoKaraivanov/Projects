package com.example.employeeportal.service;

import com.example.employeeportal.model.dtos.CreateTaskDTO;
import com.example.employeeportal.model.entity.TaskEntity;
import com.example.employeeportal.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean createTask(CreateTaskDTO createTaskDTO) {

        Optional<TaskEntity> byTitle = this.taskRepository.findByTitle(createTaskDTO.getTitle());
        if (byTitle.isPresent()) {
            return false;
        }

        if (createTaskDTO.getDueDate() == null) {
            return false;
        }

        TaskEntity task = new TaskEntity()
                .setTitle(createTaskDTO.getTitle())
                .setDescription(createTaskDTO.getDescription())
                .setDueDate(createTaskDTO.getDueDate());

        taskRepository.save(task);

        return true;
    }
}
