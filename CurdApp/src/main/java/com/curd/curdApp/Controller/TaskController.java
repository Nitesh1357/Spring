package com.curd.curdApp.Controller;

import com.curd.curdApp.Repository.TaskRepository;
import com.curd.curdApp.Entity.User;
import com.curd.curdApp.Entity.UserTask;
import com.curd.curdApp.Repository.UserTaskRepository;
import com.curd.curdApp.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final UserTaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(UserTaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Create a task for a specific user")
    @ApiResponse(responseCode = "200", description = "Task created successfully")
    @PostMapping("/user/{userId}")
    public ResponseEntity<UserTask> createTaskForUser(@PathVariable Long userId, @RequestBody UserTask task) {
        logger.info("Creating task for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found", userId);
                    return new RuntimeException("User not found");
                });

        task.setUser(user);
        UserTask savedTask = taskRepository.save(task);

        logger.info("Task '{}' created for user '{}'", savedTask.getTitle(), user.getEmail());

        return ResponseEntity.ok(savedTask);
    }

    @Operation(summary = "Get all tasks for a user")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTask>> getTasksForUser(@PathVariable Long userId) {
        logger.info("Fetching tasks for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found", userId);
                    return new RuntimeException("User not found");
                });

        List<UserTask> tasks = user.getTasks();

        logger.info("Found {} task(s) for user '{}'", tasks.size(), user.getEmail());

        return ResponseEntity.ok(tasks);
    }
}

