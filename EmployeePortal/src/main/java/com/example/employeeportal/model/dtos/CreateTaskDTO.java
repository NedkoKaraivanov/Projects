package com.example.employeeportal.model.dtos;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CreateTaskDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Future
    private LocalDate dueDate;

    public String getTitle() {
        return title;
    }

    public CreateTaskDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateTaskDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public CreateTaskDTO setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
