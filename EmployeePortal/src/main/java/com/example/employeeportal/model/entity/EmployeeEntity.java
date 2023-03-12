package com.example.employeeportal.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class EmployeeEntity extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private LocalDate birthDate;

    @Column(nullable = false)
    private Double salary;

    @ManyToOne
    private EmployeeEntity manager;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getBirthDate(), that.getBirthDate()) &&
                Objects.equals(getSalary(), that.getSalary()) &&
                Objects.equals(getManager(), that.getManager());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getBirthDate(), getSalary(), getManager());
    }
}
