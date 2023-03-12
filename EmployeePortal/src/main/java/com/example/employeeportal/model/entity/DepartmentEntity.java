package com.example.employeeportal.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class DepartmentEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(optional = false)
    private EmployeeEntity manager;

    public String getName() {
        return name;
    }

    public DepartmentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeEntity getManager() {
        return manager;
    }

    public DepartmentEntity setManager(EmployeeEntity manager) {
        this.manager = manager;
        return this;
    }
}
