package com.example.employeeportal.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class EmployeeEntity extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private LocalDate birthDate;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Double salary;

    @ManyToOne
    private EmployeeEntity manager;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<TaskEntity> tasks = new ArrayList<>();

    @ManyToOne
    private DepartmentEntity department;

    public String getFirstName() {
        return firstName;
    }

    public EmployeeEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EmployeeEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public EmployeeEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public EmployeeEntity setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public EmployeeEntity setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeEntity getManager() {
        return manager;
    }

    public EmployeeEntity setManager(EmployeeEntity manager) {
        this.manager = manager;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public EmployeeEntity setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public EmployeeEntity setDepartment(DepartmentEntity department) {
        this.department = department;
        return this;
    }
}
