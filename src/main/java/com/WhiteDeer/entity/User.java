package com.WhiteDeer.entity;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String id;
    private String name;
    private String phoneNumber;
    private String password;
    private Set<String> groupSet = new HashSet<>();
    private Set<String> yesTaskSet = new HashSet<>();
    private Set<String> noTaskSet = new HashSet<>();

    public User() {}

    public User( String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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