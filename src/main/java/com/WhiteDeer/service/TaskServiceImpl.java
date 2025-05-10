package com.WhiteDeer.service.impl;

import com.WhiteDeer.entity.Task;
import com.WhiteDeer.exception.TaskNotFoundException;
import com.WhiteDeer.mapper.dto.TaskCheckInDto;
import com.WhiteDeer.mapper.dto.TaskDto;
import com.WhiteDeer.repository.TaskRepository;
import com.WhiteDeer.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

/**
 * 任务服务实现类
 * 实现TaskService接口定义的所有功能
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Task add(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(String taskId) {
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId);
        }
        return task;
    }

    @Override
    @Transactional
    public Task edit(TaskDto taskDto) {
        Task existingTask = getTask(taskDto.getId());
        BeanUtils.copyProperties(taskDto, existingTask);
        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public void delete(String taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional
    public Task updateCheckInStatus(TaskCheckInDto checkInDto) {
        Task task = getTask(checkInDto.getTaskId());
        if (checkInDto.isCompleted()) {
            taskRepository.addCompletedUser(checkInDto.getTaskId(), checkInDto.getUserId());
            taskRepository.removeUncompletedUser(checkInDto.getTaskId(), checkInDto.getUserId());
        } else {
            taskRepository.addUncompletedUser(checkInDto.getTaskId(), checkInDto.getUserId());
            taskRepository.removeCompletedUser(checkInDto.getTaskId(), checkInDto.getUserId());
        }
        return getTask(checkInDto.getTaskId());
    }

    @Override
    public int[] getCheckInStatistics(String taskId) {
        Task task = getTask(taskId);
        return new int[] {
                task.getCompletedUserIds().size(),
                task.getUncompletedUserIds().size()
        };
    }

    @Override
    public Set<String> getCompletedUsers(String taskId) {
        return getTask(taskId).getCompletedUserIds();
    }

    @Override
    public Set<String> getUncompletedUsers(String taskId) {
        return getTask(taskId).getUncompletedUserIds();
    }

    @Override
    public boolean checkUserCompletionStatus(String taskId, String userId) {
        return getTask(taskId).isUserCompleted(userId);
    }
}