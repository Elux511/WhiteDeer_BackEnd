package com.WhiteDeer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String phoneNumber;
    private String password;
    private Set<String> groupSet = new HashSet<>();
    private Set<String> yesTaskSet = new HashSet<>();
    private Set<String> noTaskSet = new HashSet<>();
    public User() {}

    public User(String id, String name, String phoneNumber, String password) {
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

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.matches("^1[3-9]\\d{9}$")) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getGroupSet() {
        return new HashSet<>(groupSet);
    }

    public Set<String> getYesTaskSet() {
        return new HashSet<>(yesTaskSet);
    }

    public Set<String> getNoTaskSet() {
        return new HashSet<>(noTaskSet);
    }

    // 操作方法
    public void addGroup(String groupId) {
        groupSet.add(groupId);
    }

    public void removeGroup(String groupId) {
        groupSet.remove(groupId);
    }

    public void addYesTask(String taskId) {
        yesTaskSet.add(taskId);
        noTaskSet.remove(taskId);
    }

    public void removeYesTask(String taskId) {
        yesTaskSet.remove(taskId);
    }

    public void addNoTask(String taskId) {
        noTaskSet.add(taskId);
        yesTaskSet.remove(taskId);
    }

    public void removeNoTask(String taskId) {
        noTaskSet.remove(taskId);
    }

    public boolean isInGroup(String groupId) {
        return groupSet.contains(groupId);
    }

    public boolean hasCompletedTask(String taskId) {
        return yesTaskSet.contains(taskId);
    }

    public boolean hasNotCompletedTask(String taskId) {
        return noTaskSet.contains(taskId);
    }
}