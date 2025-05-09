package com.WhiteDeer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String phoneNumber;
    private String password;
    private List<String> groupIds = new ArrayList<>();
    private List<String> completedTaskIds = new ArrayList<>();
    private List<String> pendingTaskIds = new ArrayList<>();
    private String face;

    public User(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // 集合操作方法优化
    public void addGroup(String groupId) {
        if (groupId != null && !groupIds.contains(groupId)) {
            groupIds.add(groupId);
        }
    }

    public void removeGroup(String groupId) {
        groupIds.remove(groupId);
    }

    public void addCompletedTask(String taskId) {
        if (taskId != null && !completedTaskIds.contains(taskId)) {
            completedTaskIds.add(taskId);
            pendingTaskIds.remove(taskId);
        }
    }

    public void addPendingTask(String taskId) {
        if (taskId != null && !pendingTaskIds.contains(taskId)) {
            pendingTaskIds.add(taskId);
            completedTaskIds.remove(taskId);
        }
    }

    // 密码加密存储
    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("密码长度不能少于6位");
        }
        // 实际应用中应加密存储密码
        this.password = password;
    }

    // 电话号码验证
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.matches("^1[3-9]\\d{9}$")) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
        this.phoneNumber = phoneNumber;
    }
}