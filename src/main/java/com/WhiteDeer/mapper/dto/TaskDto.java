package com.WhiteDeer.mapper.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TaskDto {

    private String taskId;
    @NotBlank(message="打卡开始时间不能为空")
    private Timestamp beginTime;
    @NotBlank(message="打卡结束时间不能为空")
    private Timestamp endTime;
    @NotBlank(message="打卡范围大小不能为空")
    private int range;
    @NotBlank(message="打卡中心地点不能为空")
    private BigDecimal location;
    @NotBlank(message="打卡名称不能为空")
    private String name;
    @NotBlank(message="打卡组不能为空")
    private String group;
    private String yesUser;
    private String noUser;

    public String getNoUser() {
        return noUser;
    }
    public void setNoUser(String noUser) {
        this.noUser = noUser;
    }

    public String getYesUser() {
        return yesUser;
    }
    public void setYesUser(String yesUser) {
        this.yesUser = yesUser;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLocation() {
        return location;
    }
    public void setLocation(BigDecimal location) {
        this.location = location;
    }

    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "taskId='" + taskId + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", range=" + range +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", yesUser='" + yesUser + '\'' +
                ", noUser='" + noUser + '\'' +
                '}';
    }

}