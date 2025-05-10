package com.WhiteDeer.mapper.dto;

import com.WhiteDeer.entity.Task.CheckInMethod;
import java.time.LocalTime;

public class TaskDto {
    private String id;
    private String name;
    private String groupId;
    private LocalTime beginTime;
    private LocalTime endTime;
    private CheckInMethod method;

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
}