package com.it.mapper;

import jakarta.persistence.*;

@Table(name = "group_member_table")
@Entity
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private String groupMemberId;

    @Column(name = "User")
    private String user;

    @Column(name = "Member_type")
    private String memberType;

    @Column(name = "join_time")
    private String joinTime;

    // 以下是各属性的getter和setter方法
    public String getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(String groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}