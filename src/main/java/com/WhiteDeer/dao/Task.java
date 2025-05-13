package com.WhiteDeer.dao;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Vector;


@Entity
@Table(name="tasks")
public class Task {
    @Id
    @Column(name="task_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="group_id")
    private long groupId;

    @Column(name="group_name")
    private String groupName;

    @Column(name="task_name")
    private String name;

    @Column(name = "begin_time")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime endTime;

    @Column(name="completed_user_list")
    @JdbcTypeCode(SqlTypes.JSON)
    private Vector<Long> completedUserList;

    @Column(name="incomplete_user_list")
    @JdbcTypeCode(SqlTypes.JSON)
    private Vector<Long> incompleteUserList;

    @Column(name="type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private double latitude;

    @Column(name = "longitude")
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private double longitude;

    @Column(name="accuracy")
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private double accuracy;

    @Column(name = "isQRcode")
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    private boolean isQRcode;

    @Column(name="should_count")
    private int shouldCount;

    @Column(name="actual_count")
    private int actualCount;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Vector<Long> getIncompleteUserList() {
        return incompleteUserList;
    }
    public void setIncompleteUserList(Vector<Long> incompleteUserList) {
        this.incompleteUserList = incompleteUserList;
    }

    public Vector<Long> getCompletedUserList() {
        return completedUserList;
    }
    public void setCompletedUserList(Vector<Long> completedUserList) {
        this.completedUserList = completedUserList;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public long getGroupId() {
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isQRcode() {
        return isQRcode;
    }
    public void setQRcode(boolean QRcode) {
        isQRcode = QRcode;
    }

    public int getShouldCount() {
        return shouldCount;
    }
    public void setShouldCount(int shouldCount) {
        this.shouldCount = shouldCount;
    }

    public int getActualCount() {
        return actualCount;
    }
    public void setActualCount(int actualCount) {
        this.actualCount = actualCount;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void addCompletedUser(long userId) {
        if(completedUserList == null) {
            completedUserList = new Vector<>();
        }
        completedUserList.add(userId);
    }
    public void addIncompleteUser(long userId) {
        if(incompleteUserList == null) {
            incompleteUserList = new Vector<>();
        }
        incompleteUserList.add(userId);
    }
    public void deleteCompletedUser(long userId) {
        completedUserList.remove(userId);
    }
    public void deleteIncompleteUser(long userId) {
        incompleteUserList.remove(userId);
    }

}
