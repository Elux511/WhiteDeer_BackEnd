package com.WhiteDeer.mapper.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
public class GroupDto {


    private String groupId;

    private String groupName;

    private String groupIntroduction;

    private String memberList;

    private String yesTaskSet;

    private String noTaskSet;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public void setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
    }

    public String getMemberList() {
        return memberList;
    }

    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    public String getYesTaskSet() {
        return yesTaskSet;
    }

    public void setYesTaskSet(String yesTaskSet) {
        this.yesTaskSet = yesTaskSet;
    }

    public String getNoTaskSet() {
        return noTaskSet;
    }

    public void setNoTaskSet(String noTaskSet) {
        this.noTaskSet = noTaskSet;
    }



    @Override
    public String toString() {
        return "GroupDto{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupIntroduction='" + groupIntroduction + '\'' +
                ", memberList='" + memberList + '\'' +
                ", yesTaskSet='" + yesTaskSet + '\'' +
                ", noTaskSet='" + noTaskSet + '\'' +
                '}';
    }

}
