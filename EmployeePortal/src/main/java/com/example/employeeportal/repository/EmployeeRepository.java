package com.example.employeeportal.repository;

import com.example.employeeportal.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmail(String email);

    Optional<EmployeeEntity> findByPhoneNumber(String phoneNumber);

    List<EmployeeEntity> findAllByAndEmailNot(String email);

    List<EmployeeEntity> findAllByAndPhoneNumberNot(String phoneNumber);
}
