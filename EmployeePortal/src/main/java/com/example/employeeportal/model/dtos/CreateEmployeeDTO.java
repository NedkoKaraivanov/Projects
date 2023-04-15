package com.example.employeeportal.model.dtos;

import jakarta.validation.constraints.*;


public class CreateEmployeeDTO {

    private Long id;
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    private String birthDate;

    @Positive
    @NotNull
    private Double salary;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    private Long department;

    private Long manager;

    public Long getId() {
        return id;
    }

    public CreateEmployeeDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getDepartment() {
        return department;
    }

    public CreateEmployeeDTO setDepartment(Long department) {
        this.department = department;
        return this;
    }

    public Long getManager() {
        return manager;
    }

    public CreateEmployeeDTO setManager(Long manager) {
        this.manager = manager;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CreateEmployeeDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreateEmployeeDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateEmployeeDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CreateEmployeeDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public CreateEmployeeDTO setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public CreateEmployeeDTO setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public CreateEmployeeDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public CreateEmployeeDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
