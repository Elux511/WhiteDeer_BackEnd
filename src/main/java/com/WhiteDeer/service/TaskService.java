package com.WhiteDeer.service;

import com.WhiteDeer.entity.Task;
import com.WhiteDeer.exception.TaskNotFoundException;
import com.WhiteDeer.mapper.dto.TaskCheckInDto;
import com.WhiteDeer.mapper.dto.TaskDto;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

/**
 * 任务服务接口
 * 定义任务管理的核心操作契约
 */
public interface TaskService {
    // 添加任务
    @Transactional
    Task add(TaskDto taskDto);

    // 根据ID获取任务
    Task getTask(String taskId) throws TaskNotFoundException;

    // 编辑任务
    @Transactional
    Task edit(TaskDto taskDto);

    // 删除任务
    @Transactional
    void delete(String taskId);

    // 更新任务签到状态
    @Transactional
    Task updateCheckInStatus(TaskCheckInDto checkInDto);

    // 获取签到统计（完成/未完成人数）
    int[] getCheckInStatistics(String taskId);

    // 获取已完成用户集合
    Set<String> getCompletedUsers(String taskId);

    // 获取未完成用户集合
    Set<String> getUncompletedUsers(String taskId);

    // 检查用户完成状态
    boolean checkUserCompletionStatus(String taskId, String userId);
}