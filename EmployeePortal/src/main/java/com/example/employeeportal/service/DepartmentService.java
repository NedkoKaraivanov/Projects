package com.example.employeeportal.service;

import com.example.employeeportal.model.entity.DepartmentEntity;
import com.example.employeeportal.model.enums.DepartmentEnum;
import com.example.employeeportal.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void initDepartments() {

        if (departmentRepository.count() == 0) {
            Arrays.stream(DepartmentEnum.values())
                    .map(depEnum -> {
                        return new DepartmentEntity()
                                .setName(depEnum);
                    })
                    .forEach(departmentRepository::save);
        }
    }

    public List<DepartmentEntity> findAllDepartments() {
        return departmentRepository.findAll();
    }
}
