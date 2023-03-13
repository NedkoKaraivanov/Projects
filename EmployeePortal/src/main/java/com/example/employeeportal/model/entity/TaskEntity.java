package com.example.employeeportal.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDate dueDate;

    @ManyToOne
    private EmployeeEntity employee;

    public String getTitle() {
        return title;
    }

    public TaskEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskEntity setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public TaskEntity setEmployee(EmployeeEntity employee) {
        this.employee = employee;
        return this;
    }
}
