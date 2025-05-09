package com.WhiteDeer.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Task {
    public enum CheckInMethod {
        FACE_RECOGNITION,
        GEO_FENCING,
        BOTH
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String groupId;
    private LocalTime beginTime;
    private LocalTime endTime;
    private CheckInMethod method;
    private Set<String> completedUserIds = new HashSet<>();
    private Set<String> uncompletedUserIds = new HashSet<>();

    public Task() {}

    public Task(String id, String name, String groupId, LocalTime beginTime,
                LocalTime endTime, CheckInMethod method) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.method = method;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public CheckInMethod getMethod() {
        return method;
    }

    public void setMethod(CheckInMethod method) {
        this.method = method;
    }

    public Set<String> getCompletedUserIds() {
        return new HashSet<>(completedUserIds);
    }

    public Set<String> getUncompletedUserIds() {
        return new HashSet<>(uncompletedUserIds);
    }

    public boolean markAsCompleted(String userId) {
        if (completedUserIds.contains(userId)) {
            return false;
        }
        completedUserIds.add(userId);
        uncompletedUserIds.remove(userId);
        return true;
    }

    public boolean markAsUncompleted(String userId) {
        if (uncompletedUserIds.contains(userId)) {
            return false;
        }
        uncompletedUserIds.add(userId);
        completedUserIds.remove(userId);
        return true;
    }

    public void resetUserStatus(String userId) {
        completedUserIds.remove(userId);
        uncompletedUserIds.remove(userId);
    }

    public boolean isUserCompleted(String userId) {
        return completedUserIds.contains(userId);
    }

    public boolean isUserUncompleted(String userId) {
        return uncompletedUserIds.contains(userId);
    }
}