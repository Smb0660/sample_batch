package com.example.samplebatch.repository;

import com.example.samplebatch.model.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<EmployeeInfo,Long> {
}
