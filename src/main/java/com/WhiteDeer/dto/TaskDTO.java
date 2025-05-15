package com.WhiteDeer.dto;

import com.WhiteDeer.dao.Task;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Vector;

public class TaskDTO {
    private long id;
    private long groupId;
    private String groupName;
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String type;
    private String description;
    private double latitude;
    private double longitude;
    private double accuracy;
    private int shouldCount;
    private int actualCount;
    private boolean isQRcode;
    private String status;
    private Blob face;
    private Vector<Long> completedUserList;
    private Vector<Long> incompleteUserList;
    private Vector<String> completedNameList;
    private Vector<String> incompleteNameList;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

    public boolean isQRcode() {
        return isQRcode;
    }
    public void setQRcode(boolean QRcode) {
        isQRcode = QRcode;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Blob getFace() {
        return face;
    }
    public void setFace(Blob face) {
        this.face = face;
    }

    public Vector<Long> getCompletedUserList() {
        return completedUserList;
    }
    public void setCompletedUserList(Vector<Long> completedUserList) {
        this.completedUserList = completedUserList;
    }

    public Vector<Long> getIncompleteUserList() {
        return incompleteUserList;
    }
    public void setIncompleteUserList(Vector<Long> incompleteUserList) {
        this.incompleteUserList = incompleteUserList;
    }

    public Vector<String> getCompletedNameList() {
        return completedNameList;
    }
    public void setCompletedNameList(Vector<String> completedNameList) {
        this.completedNameList = completedNameList;
    }

    public Vector<String> getIncompleteNameList() {
        return incompleteNameList;
    }
    public void setIncompleteNameList(Vector<String> incompleteNameList) {
        this.incompleteNameList = incompleteNameList;
    }
}
