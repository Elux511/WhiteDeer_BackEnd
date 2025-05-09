package com.WhiteDeer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "begin_time", nullable = false)
    private Timestamp beginTime;

    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    @Column(name = "range")
    private int range;

    @Column(name = "location")
    private BigDecimal location;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "yes_user")
    private String yesUser;

    @Column(name = "no_user")
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