package com.WhiteDeer.dao;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Vector;


@Entity
@Data
@Table(name = "groupinfo")
public class GroupInfo {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    @Column(name = "group_name", length = 20, nullable = false)
    private String groupName;

    @Column(name = "group_introduction", length = 255, nullable = true)
    private String groupIntroduction;

    @Column(name = "member_list")
    @JdbcTypeCode(SqlTypes.JSON)
    private Vector<Long> memberList;

    @Column(name = "yes_task_set")
    @JdbcTypeCode(SqlTypes.JSON)
    private Vector<Long> yesTaskSet;

    @Column(name = "no_task_set")
    @JdbcTypeCode(SqlTypes.JSON)
    private Vector<Long> noTaskSet;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "max_member", columnDefinition = "INT DEFAULT 0")
    private Long maxMember;

    @Column(name = "create_time", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime createTime;

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

    public long getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(long maxMember) {
        this.maxMember = maxMember;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void addYesTaskSet(Long taskId) {
        if(yesTaskSet == null) {
            yesTaskSet = new Vector<>();
        }
        yesTaskSet.add(taskId);
    }
    public void addNoTaskSet(Long taskId) {
        if(noTaskSet == null) {
            noTaskSet = new Vector<>();
        }
        noTaskSet.add(taskId);
    }
    public void deteleYesTaskSet(Long taskId) {
        yesTaskSet.remove(taskId);
    }
    public void deteleNoTaskSet(Long taskId) {
        noTaskSet.remove(taskId);
    }
}