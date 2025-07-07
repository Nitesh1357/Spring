package com.curd.curdApp.Repository;
import com.curd.curdApp.Entity.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
}
