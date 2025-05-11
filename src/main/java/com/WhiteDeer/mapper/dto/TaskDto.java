package com.WhiteDeer.mapper.dto;

import com.WhiteDeer.entity.Task.CheckInMethod;
import java.time.LocalTime;

public class TaskDto {
    private String id;
    private String name;
    private String group_id;
    private LocalTime begin_time;
    private LocalTime end_time;
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
        return group_id;
    }

    public void setGroupId(String groupId) {
        this.group_id = groupId;
    }

    public LocalTime getBeginTime() {
        return begin_time;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.begin_time = beginTime;
    }

    public LocalTime getEndTime() {
        return end_time;
    }

    public void setEndTime(LocalTime endTime) {
        this.end_time = endTime;
    }

    public CheckInMethod getMethod() {
        return method;
    }

    public void setMethod(CheckInMethod method) {
        this.method = method;
    }
}