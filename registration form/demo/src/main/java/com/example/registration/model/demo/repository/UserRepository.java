package com.example.registration.model.demo.repository;

import com.example.registration.model.demo.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserDetails, Integer> {
}
