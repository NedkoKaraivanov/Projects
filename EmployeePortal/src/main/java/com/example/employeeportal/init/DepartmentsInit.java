package com.example.employeeportal.init;

import com.example.employeeportal.service.DepartmentService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DepartmentsInit {

    private final DepartmentService departmentService;

    public DepartmentsInit(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostConstruct
    public void initDepartments() {
        departmentService.initDepartments();

    }
}
