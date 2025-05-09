package com.WhiteDeer.service;

import com.WhiteDeer.entity.Task;
import com.WhiteDeer.exception.TaskNotFoundException;
import com.WhiteDeer.mapper.dto.TaskCheckInDto;
import com.WhiteDeer.mapper.dto.TaskDto;
import com.WhiteDeer.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task add(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        return taskRepository.save(task);
    }

    public Task getTask(String taskId) {
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId);
        }
        return task;
    }

    @Transactional
    public Task edit(TaskDto taskDto) {
        Task existingTask = getTask(taskDto.getId());
        BeanUtils.copyProperties(taskDto, existingTask);
        return taskRepository.save(existingTask);
    }

    @Transactional
    public void delete(String taskId) {
        taskRepository.deleteById(taskId);
    }

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

    public int[] getCheckInStatistics(String taskId) {
        Task task = getTask(taskId);
        return new int[] {
                task.getCompletedUserIds().size(),
                task.getUncompletedUserIds().size()
        };
    }

    public Set<String> getCompletedUsers(String taskId) {
        return getTask(taskId).getCompletedUserIds();
    }

    public Set<String> getUncompletedUsers(String taskId) {
        return getTask(taskId).getUncompletedUserIds();
    }

    public boolean checkUserCompletionStatus(String taskId, String userId) {
        return getTask(taskId).isUserCompleted(userId);
    }
}