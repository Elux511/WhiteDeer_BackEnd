package com.WhiteDeer.service;

import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dto.TaskDTO;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface TaskService {

    void updateTask(Task task);

    Task createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    void deleteTaskById(long id);

    CompletableFuture<Integer> checkinTask(TaskDTO taskDTO, long userId) throws IOException;

    void finishTaskById(long userId, long taskId) throws IOException;

}
