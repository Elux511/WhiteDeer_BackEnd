package com.WhiteDeer.service;

import com.WhiteDeer.dto.TaskDTO;

public interface TaskService {


    void createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);
}
