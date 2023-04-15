package com.example.employeeportal.service;

import com.example.employeeportal.model.dtos.CreateEmployeeDTO;
import com.example.employeeportal.model.dtos.viewDtos.ViewEmployeeDTO;
import com.example.employeeportal.model.entity.DepartmentEntity;
import com.example.employeeportal.model.entity.EmployeeEntity;
import com.example.employeeportal.repository.DepartmentRepository;
import com.example.employeeportal.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
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

        Optional<EmployeeEntity> byPhoneNumber = this.employeeRepository.findByPhoneNumber(createEmployeeDTO.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
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

    public List<ViewEmployeeDTO> findAllEmployees() {

        List<EmployeeEntity> all = employeeRepository.findAll();

        return all.stream()
                .map(employee -> {
                    return new ViewEmployeeDTO()
                            .setId(employee.getId())
                            .setFirstName(employee.getFirstName())
                            .setLastName(employee.getLastName())
                            .setEmail(employee.getEmail())
                            .setPhoneNumber(employee.getPhoneNumber())
                            .setSalary(employee.getSalary());
                }).collect(Collectors.toList());
    }

    public void deleteEmployee(Long id) {

        this.employeeRepository.deleteById(id);
    }

    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public boolean saveEmployee(EmployeeEntity employee) {

        List<EmployeeEntity> byEmail = this.employeeRepository.findAllByAndEmailNot(employee.getEmail());
        if (byEmail.size() > 0) {
            return false;
        }

        List<EmployeeEntity> byPhoneNumber = this.employeeRepository.findAllByAndPhoneNumberNot(employee.getPhoneNumber());
        if (byPhoneNumber.size() > 0) {
            return false;
        }

        employeeRepository.save(employee);

        return true;
    }

    public List<EmployeeEntity> findAllManagers() {
        return employeeRepository.findAll();
    }
}
