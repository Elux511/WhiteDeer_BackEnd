package com.example; // 请根据实际包名调整

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Task")
public class Task {

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "begin_time")
    private Timestamp beginTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "Range")
    private int range;

    @Column(name = "Location")
    private BigDecimal location;

    @Column(name = "name")
    private String name;

    @Column(name = "Group")
    private String group;

    @Column(name = "Yes_user")
    private String yesUser;

    @Column(name = "No_user")
    private String noUser;

    //task_id
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    //Begin_time
    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }
    //endtime
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    //range
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    //location
    public BigDecimal getLocation() {
        return location;
    }

    public void setLocation(BigDecimal location) {
        this.location = location;
    }
    //name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //group
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    //yes_user
    public String getYesUser() {
        return yesUser;
    }

    public void setYesUser(String yesUser) {
        this.yesUser = yesUser;
    }
    //no_user
    public String getNoUser() {
        return noUser;
    }

    public void setNoUser(String noUser) {
        this.noUser = noUser;
    }
}