package com.example.employeeportal.service;

import com.example.employeeportal.model.dtos.CreateEmployeeDTO;
import com.example.employeeportal.model.entity.EmployeeEntity;
import com.example.employeeportal.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createEmployee(CreateEmployeeDTO createEmployeeDTO) {

        if (!createEmployeeDTO.getPassword().equals(createEmployeeDTO.getConfirmPassword())) {
            return false;
        }

        Optional<EmployeeEntity> byEmail = this.employeeRepository.findByEmail(createEmployeeDTO.getEmail());
        if (byEmail.isPresent()) {
            return false;
        }

        Optional<EmployeeEntity> byUsername = this.employeeRepository.findByPhoneNumber(createEmployeeDTO.getPhoneNumber());
        if (byUsername.isPresent()) {
            return false;
        }

        EmployeeEntity employee = new EmployeeEntity()
                .setFirstName(createEmployeeDTO.getFirstName())
                .setLastName(createEmployeeDTO.getLastName())
                .setEmail(createEmployeeDTO.getEmail())
                .setPhoneNumber(createEmployeeDTO.getPhoneNumber())
                .setSalary(createEmployeeDTO.getSalary())
                .setPassword(passwordEncoder.encode(createEmployeeDTO.getPassword()));

        if (createEmployeeDTO.getBirthDate() != null) {
            LocalDate birthDate = LocalDate.parse(createEmployeeDTO.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            employee.setBirthDate(birthDate);
        }

        this.employeeRepository.save(employee);

        return true;
    }
}
