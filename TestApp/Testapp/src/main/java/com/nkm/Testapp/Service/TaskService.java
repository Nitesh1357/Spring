package com.nkm.Testapp.Service;

import com.nkm.Testapp.DTO.TaskDTO;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, Long userId);
}
