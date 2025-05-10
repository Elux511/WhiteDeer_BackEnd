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

/**
 * 任务管理控制器
 * 处理任务相关的所有HTTP请求
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 创建新任务
     * @param taskDto 任务数据传输对象（包含标题、描述等信息）
     * @return 创建成功的任务数据
     */
    @PostMapping
    public ResponseMessage createTask(@Validated @RequestBody TaskDto taskDto) {
        Task task = taskService.add(taskDto);
        return ResponseMessage.success(task);
    }

    /**
     * 获取指定任务详情
     * @param taskId 任务ID
     * @return 任务详细信息
     */
    @GetMapping("/{taskId}")
    public ResponseMessage getTask(@PathVariable String taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseMessage.success(task);
    }

    /**
     * 更新任务信息
     * @param taskDto 包含更新数据的任务DTO
     * @return 更新后的任务数据
     */
    @PutMapping
    public ResponseMessage updateTask(@Validated @RequestBody TaskDto taskDto) {
        Task task = taskService.edit(taskDto);
        return ResponseMessage.success(task);
    }

    /**
     * 删除指定任务
     * @param taskId 要删除的任务ID
     * @return 空响应（操作状态）
     */
    @DeleteMapping("/{taskId}")
    public ResponseMessage deleteTask(@PathVariable String taskId) {
        taskService.delete(taskId);
        return ResponseMessage.success();
    }

    /**
     * 更新任务签到状态
     * @param checkInDto 包含任务ID、用户ID和完成状态
     * @return 更新后的任务数据
     */
    @PostMapping("/check-in")
    public ResponseMessage checkInTask(@Validated @RequestBody TaskCheckInDto checkInDto) {
        Task task = taskService.updateCheckInStatus(checkInDto);
        return ResponseMessage.success(task);
    }

    /**
     * 获取任务签到统计
     * @param taskId 任务ID
     * @return 包含完成/未完成人数的统计对象
     */
    @GetMapping("/{taskId}/statistics")
    public ResponseMessage getTaskStatistics(@PathVariable String taskId) {
        int[] stats = taskService.getCheckInStatistics(taskId);
        return ResponseMessage.success(new TaskStatistics(stats[0], stats[1]));
    }

    /**
     * 获取已完成任务的用户列表
     * @param taskId 任务ID
     * @return 用户ID集合
     */
    @GetMapping("/{taskId}/completed-users")
    public ResponseMessage getTaskCompletedUsers(@PathVariable String taskId) {
        Set<String> users = taskService.getCompletedUsers(taskId);
        return ResponseMessage.success(users);
    }

    /**
     * 获取未完成任务的用户列表
     * @param taskId 任务ID
     * @return 用户ID集合
     */
    @GetMapping("/{taskId}/uncompleted-users")
    public ResponseMessage getTaskUncompletedUsers(@PathVariable String taskId) {
        Set<String> users = taskService.getUncompletedUsers(taskId);
        return ResponseMessage.success(users);
    }

    /**
     * 检查用户任务完成状态
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 完成状态字符串（"completed"/"uncompleted"）
     */
    @GetMapping("/{taskId}/users/{userId}/status")
    public ResponseMessage getUserTaskStatus(
            @PathVariable String taskId,
            @PathVariable String userId) {
        boolean isCompleted = taskService.checkUserCompletionStatus(taskId, userId);
        return ResponseMessage.success(isCompleted ? "completed" : "uncompleted");
    }

    /**
     * 任务统计内部类
     */
    private static class TaskStatistics {
        private final int completed;
        private final int uncompleted;

        public TaskStatistics(int completed, int uncompleted) {
            this.completed = completed;
            this.uncompleted = uncompleted;
        }

        public int getCompleted() {
            return completed;
        }

        public int getUncompleted() {
            return uncompleted;
        }
    }
}