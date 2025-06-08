package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<User_info, Long> {
    Optional<User_info> findByUserId(Long Id);
}