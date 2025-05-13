package com.WhiteDeer.service;

import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dao.TaskRepository;
import com.WhiteDeer.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void createTask(TaskDTO taskDTO) {
        taskRepository.save(TaskConverter.convertTask(taskDTO));
    }

    @Override
    public TaskDTO getTaskById(Long id) {
       System.out.println(4);
       Task task = taskRepository.getById(id);
       System.out.println(5);
       return TaskConverter.convertTask(task);
    }
}
