//package com.nkm.Testapp.ServiceImpl;
//
//
//import com.nkm.Testapp.DTO.TaskDTO;
//import com.nkm.Testapp.Entity.*;
//import com.nkm.Testapp.Repository.*;
//import com.nkm.Testapp.Service.TaskService;
////import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TaskServiceImpl implements TaskService {
//
//    private final TaskRepository taskRepo;
//    private final UserRepository userRepo;
//    private final ModelMapper modelMapper;
//
//    public TaskServiceImpl(TaskRepository taskRepo, UserRepository userRepo, ModelMapper modelMapper) {
//        this.taskRepo = taskRepo;
//        this.userRepo = userRepo;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public TaskDTO createTask(TaskDTO taskDTO, Long userId) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        Task task = modelMapper.map(taskDTO, Task.class);
//        task.setUser(user);
//
//        Task saved = taskRepo.save(task);
//        return modelMapper.map(saved, TaskDTO.class);
//    }
//}
//
