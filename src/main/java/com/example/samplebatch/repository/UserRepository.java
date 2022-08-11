package com.example.samplebatch.repository;

import com.example.samplebatch.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserInfo, Long> {
}