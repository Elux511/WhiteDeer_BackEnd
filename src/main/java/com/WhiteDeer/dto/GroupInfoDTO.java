package com.WhiteDeer.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Vector;

@Data
public class GroupInfoDTO {
    private long groupId;
    private String groupName;
    private String isFull; // 可以是"否"/"是"
    private Long memberCount;
    private String groupIntroduction;
    private Vector<Long> memberList;
    private Vector<Long> yesTaskSet;
    private Vector<Long> noTaskSet;
    private Long creatorId;
    private String createTime;


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIsFull() {
        return isFull;
    }

    public void setIsFull(String isFull) {
        this.isFull = isFull;
    }

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public void setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
    }

    public Vector<Long> getMemberList() {
        return memberList;
    }

    public void setMemberList(Vector<Long> memberList) {
        this.memberList = memberList;
    }

    public Vector<Long> getYesTaskSet() {
        return yesTaskSet;
    }

    public void setYesTaskSet(Vector<Long> yesTaskSet) {
        this.yesTaskSet = yesTaskSet;
    }

    public Vector<Long> getNoTaskSet() {
        return noTaskSet;
    }

    public void setNoTaskSet(Vector<Long> noTaskSet) {
        this.noTaskSet = noTaskSet;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }


}
