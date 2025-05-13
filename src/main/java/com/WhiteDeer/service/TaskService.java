package com.WhiteDeer.service;

import com.WhiteDeer.dto.TaskDTO;

import java.io.IOException;

public interface TaskService {


    void createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    void deleteUserById(long id);

    int checkinTask(TaskDTO taskDTO,long userId) throws IOException;
}
