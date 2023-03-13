package com.example.employeeportal.model.dtos.viewDtos;

public class ViewTaskDTO {

    private Long id;
    private String title;

    private String description;

    private String dueDate;

    private String employeeFullName;

    public Long getId() {
        return id;
    }

    public ViewTaskDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ViewTaskDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ViewTaskDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public ViewTaskDTO setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public ViewTaskDTO setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
        return this;
    }
}
