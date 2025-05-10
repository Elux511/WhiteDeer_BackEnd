package com.WhiteDeer.controller;

import com.WhiteDeer.entity.Task;
import com.WhiteDeer.mapper.dto.TaskCheckInDto;
import com.WhiteDeer.mapper.dto.TaskDto;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseMessage add(@Validated @RequestBody TaskDto taskDto) {
        Task task = taskService.add(taskDto);
        return ResponseMessage.success(task);
    }

    @GetMapping("/{taskId}")
    public ResponseMessage get(@PathVariable String taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseMessage.success(task);
    }

    @PutMapping
    public ResponseMessage edit(@Validated @RequestBody TaskDto taskDto) {
        Task task = taskService.edit(taskDto);
        return ResponseMessage.success(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseMessage delete(@PathVariable String taskId) {
        taskService.delete(taskId);
        return ResponseMessage.success();
    }

    @PostMapping("/check-in")
    public ResponseMessage updateCheckInStatus(@Validated @RequestBody TaskCheckInDto checkInDto) {
        Task task = taskService.updateCheckInStatus(checkInDto);
        return ResponseMessage.success(task);
    }

    @GetMapping("/{taskId}/statistics")
    public ResponseMessage getCheckInStatistics(@PathVariable String taskId) {
        int[] stats = taskService.getCheckInStatistics(taskId);
        return ResponseMessage.success(new CheckInStatistics(stats[0], stats[1]));
    }

    @GetMapping("/{taskId}/completed-users")
    public ResponseMessage getCompletedUsers(@PathVariable String taskId) {
        Set<String> users = taskService.getCompletedUsers(taskId);
        return ResponseMessage.success(users);
    }

    @GetMapping("/{taskId}/uncompleted-users")
    public ResponseMessage getUncompletedUsers(@PathVariable String taskId) {
        Set<String> users = taskService.getUncompletedUsers(taskId);
        return ResponseMessage.success(users);
    }

    @GetMapping("/{taskId}/users/{userId}/status")
    public ResponseMessage checkUserStatus(
            @PathVariable String taskId,
            @PathVariable String userId) {
        boolean isCompleted = taskService.checkUserCompletionStatus(taskId, userId);
        return ResponseMessage.success(isCompleted ? "completed" : "uncompleted");
    }

    private static class CheckInStatistics {
        private final int completedCount;
        private final int uncompletedCount;

        public CheckInStatistics(int completedCount, int uncompletedCount) {
            this.completedCount = completedCount;
            this.uncompletedCount = uncompletedCount;
        }

        public int getCompletedCount() {
            return completedCount;
        }

        public int getUncompletedCount() {
            return uncompletedCount;
        }
    }
}