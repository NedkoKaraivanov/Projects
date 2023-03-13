package com.example.employeeportal.model.dtos.viewDtos;


public class ViewEmployeeDTO {

    private Long id;
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String birthDate;

    private Double salary;

    private String department;

    public Long getId() {
        return id;
    }

    public ViewEmployeeDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ViewEmployeeDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ViewEmployeeDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ViewEmployeeDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ViewEmployeeDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public ViewEmployeeDTO setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public ViewEmployeeDTO setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public ViewEmployeeDTO setDepartment(String department) {
        this.department = department;
        return this;
    }
}
