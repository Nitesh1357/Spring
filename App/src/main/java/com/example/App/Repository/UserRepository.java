package com.example.App.Repository;

import com.example.App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

//
//package com.example.productapi.repository;
//
//import com.example.productapi.entity.Task;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface TaskRepository extends JpaRepository<Task, Long> {
//}
//
//

