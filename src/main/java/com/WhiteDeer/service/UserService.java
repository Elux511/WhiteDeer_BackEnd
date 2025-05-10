package com.WhiteDeer.service;

import com.WhiteDeer.entity.User;
import com.WhiteDeer.exception.UserNotFoundException;
import com.WhiteDeer.mapper.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

/**
 * 用户服务接口
 * 定义用户管理的核心操作契约
 */
public interface UserService {
    // 添加用户
    @Transactional
    User add(UserDto userDto);

    // 根据ID获取用户
    User getUser(String userId) throws UserNotFoundException;

    // 更新用户信息
    @Transactional
    User update(UserDto userDto);

    // 删除用户
    @Transactional
    void delete(String userId);

    // 添加用户到组
    @Transactional
    void addUserToGroup(String userId, String groupId);

    // 从组移除用户
    @Transactional
    void removeUserFromGroup(String userId, String groupId);

    // 标记任务为已完成
    @Transactional
    void markTaskAsCompleted(String userId, String taskId);

    // 标记任务为未完成
    @Transactional
    void markTaskAsNotCompleted(String userId, String taskId);

    // 获取用户所属组
    Set<String> getUserGroups(String userId);

    // 获取用户已完成任务
    Set<String> getCompletedTasks(String userId);

    // 获取用户未完成任务
    Set<String> getNotCompletedTasks(String userId);

    //face操作
    @Transactional
    void uploadFaceImage(String userId, byte[] imageData, String contentType);

    byte[] getFaceImage(String userId);

    String getFaceImageContentType(String userId);
}