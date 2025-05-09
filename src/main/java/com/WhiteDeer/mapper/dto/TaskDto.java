package com.WhiteDeer.mapper.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TaskDto {
    private Integer taskId;

    @NotBlank(message = "任务名称不能为空")
    private String name;

    @NotNull(message = "开始时间不能为空")
    private Timestamp beginTime;

    @NotNull(message = "结束时间不能为空")
    private Timestamp endTime;

    private int range;
    private BigDecimal location;
    private String groupName;
    private String yesUser;
    private String noUser;

    // Getters and Setters
    public Integer getTaskId() { return taskId; }
    public void setTaskId(Integer taskId) { this.taskId = taskId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Timestamp getBeginTime() { return beginTime; }
    public void setBeginTime(Timestamp beginTime) { this.beginTime = beginTime; }
    public Timestamp getEndTime() { return endTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public int getRange() { return range; }
    public void setRange(int range) { this.range = range; }
    public BigDecimal getLocation() { return location; }
    public void setLocation(BigDecimal location) { this.location = location; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public String getYesUser() { return yesUser; }
    public void setYesUser(String yesUser) { this.yesUser = yesUser; }
    public String getNoUser() { return noUser; }
    public void setNoUser(String noUser) { this.noUser = noUser; }
}