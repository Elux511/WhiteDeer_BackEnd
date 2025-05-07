package com.it.mapper;

import jakarta.persistence.*;

@Table(name = "group_table")
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Group_id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_introduction")
    private String groupIntroduction;

    @Column(name = "Member_list")
    private String memberList;

    @Column(name = "Yes_task_set")
    private String yesTaskSet;

    @Column(name = "No_task_set")
    private String noTaskSet;

    // group_id
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    //group_name
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
}