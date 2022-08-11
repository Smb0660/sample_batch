package com.example.samplebatch.repository;


import com.example.samplebatch.model.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentInfo,Long> {
}
