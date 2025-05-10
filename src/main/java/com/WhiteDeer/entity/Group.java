package com.WhiteDeer.entity;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private String id;
    private String name;
    private String creatorId;
    private Set<GroupMember> memberList = new HashSet<>();
    private Set<String> yesTaskSet = new HashSet<>();
    private Set<String> noTaskSet = new HashSet<>();
    private String introduction;

    public Group() {}

    public Group(String id, String name, String creatorId, String introduction) {
        this.id = id;
        this.name = name;
        this.creatorId = creatorId;
        this.introduction = introduction;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Set<GroupMember> getMemberList() {
        return new HashSet<>(memberList);
    }

    public Set<String> getYesTaskSet() {
        return new HashSet<>(yesTaskSet);
    }

    public Set<String> getNoTaskSet() {
        return new HashSet<>(noTaskSet);
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    // 操作方法
    public void addMember(GroupMember member) {
        memberList.add(member);
    }

    public void removeMember(String userId) {
        memberList.removeIf(m -> m.getUserId().equals(userId));
    }

    public void addYesTask(String taskId) {
        yesTaskSet.add(taskId);
        noTaskSet.remove(taskId);
    }

    public void removeYesTask(String taskId) {
        yesTaskSet.remove(taskId);
    }

    public void addNoTask(String taskId) {
        noTaskSet.add(taskId);
        yesTaskSet.remove(taskId);
    }

    public void removeNoTask(String taskId) {
        noTaskSet.remove(taskId);
    }

    public boolean hasMember(String userId) {
        return memberList.stream().anyMatch(m -> m.getUserId().equals(userId));
    }

    public boolean hasCompletedTask(String taskId) {
        return yesTaskSet.contains(taskId);
    }

    public boolean hasNotCompletedTask(String taskId) {
        return noTaskSet.contains(taskId);
    }
}