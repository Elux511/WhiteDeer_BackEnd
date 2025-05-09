package com.WhiteDeer.entity;

public class GroupMember {
    private String userId;
    private String role; // "admin" or "member"
    private String joinTime;

    public GroupMember() {}

    public GroupMember(String userId, String role, String joinTime) {
        this.userId = userId;
        this.role = role;
        this.joinTime = joinTime;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}