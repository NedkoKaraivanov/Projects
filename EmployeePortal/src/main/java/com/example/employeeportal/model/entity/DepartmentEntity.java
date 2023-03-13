package com.example.employeeportal.model.entity;

import com.example.employeeportal.model.enums.DepartmentEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class DepartmentEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private DepartmentEnum name;

    @OneToOne(optional = true)
    private EmployeeEntity manager;

    public DepartmentEnum getName() {
        return name;
    }

    public DepartmentEntity setName(DepartmentEnum name) {
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
