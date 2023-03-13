package com.example.employeeportal.service;

import com.example.employeeportal.model.dtos.CreateTaskDTO;
import com.example.employeeportal.model.dtos.viewDtos.ViewEmployeeDTO;
import com.example.employeeportal.model.dtos.viewDtos.ViewTaskDTO;
import com.example.employeeportal.model.entity.TaskEntity;
import com.example.employeeportal.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ViewTaskDTO> findAllTasks() {

        List<TaskEntity> all = taskRepository.findAll();

        return all.stream()
                .map(task -> {
                    ViewTaskDTO viewTask = new ViewTaskDTO()
                            .setId(task.getId())
                            .setTitle(task.getTitle())
                            .setDescription(task.getDescription())
                            .setDueDate(task.getDueDate().toString());

                    if (task.getEmployee() != null) {
                        viewTask.setEmployeeFullName(task.getEmployee().getFirstName() + " " + task.getEmployee().getLastName());
                    }

                    return viewTask;
                }).collect(Collectors.toList());
    }

    public void deleteTask(Long id) {

        taskRepository.deleteById(id);
    }

    public List<TaskEntity> findAllExistingTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity findById(Long id) {
        return taskRepository.findById(id).get();
    }

    public void saveTask(TaskEntity task) {
        taskRepository.save(task);
    }
}
