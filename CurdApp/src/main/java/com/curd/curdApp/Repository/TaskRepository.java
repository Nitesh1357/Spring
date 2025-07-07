package com.curd.curdApp.Repository;
import com.curd.curdApp.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

