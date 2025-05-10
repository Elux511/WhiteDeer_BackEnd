package com.WhiteDeer.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 任务实体类
 * 表示系统中的任务信息及其状态
 */
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * 任务签到方式枚举
     * - FACE_RECOGNITION: 人脸识别签到
     * - GEO_FENCING: 地理围栏签到
     * - BOTH: 双重验证签到
     */
    public enum CheckInMethod {
        FACE_RECOGNITION,
        GEO_FENCING,
        BOTH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private String groupId;

    private LocalTime beginTime;

    private LocalTime endTime;

    private CheckInMethod method;

    /** 已完成任务的用户ID集合 */
    @ElementCollection
    @CollectionTable(name = "task_completed_users", joinColumns = @JoinColumn(name = "task_id"))
    private Set<String> completedUserIds = new HashSet<>();

    /** 未完成任务的用户ID集合 */
    @ElementCollection
    @CollectionTable(name = "task_uncompleted_users", joinColumns = @JoinColumn(name = "task_id"))
    private Set<String> uncompletedUserIds = new HashSet<>();

    /** JPA所需的默认构造函数 */
    public Task() {}

    /**
     * 全参数构造函数
     * @param name 任务名称
     * @param groupId 关联群组ID
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param method 签到方式
     */
    public Task(String name, String groupId, LocalTime beginTime,
                LocalTime endTime, CheckInMethod method) {
        this.name = name;
        this.groupId = groupId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.method = method;
    }
    //getter and setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public LocalTime getBeginTime() { return beginTime; }
    public void setBeginTime(LocalTime beginTime) { this.beginTime = beginTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public CheckInMethod getMethod() { return method; }
    public void setMethod(CheckInMethod method) { this.method = method; }

    // =============== 业务方法 ===============
    /**
     * 获取已完成用户ID集合（安全副本）
     */
    public Set<String> getCompletedUserIds() {
        return new HashSet<>(completedUserIds);
    }

    /**
     * 获取未完成用户ID集合（安全副本）
     */
    public Set<String> getUncompletedUserIds() {
        return new HashSet<>(uncompletedUserIds);
    }

    /**
     * 标记用户任务为已完成
     * @param userId 用户ID
     * @return 是否更新成功（已存在时返回false）
     */
    public boolean markAsCompleted(String userId) {
        if (completedUserIds.contains(userId)) return false;
        completedUserIds.add(userId);
        uncompletedUserIds.remove(userId);
        return true;
    }

    /**
     * 标记用户任务为未完成
     * @param userId 用户ID
     * @return 是否更新成功（已存在时返回false）
     */
    public boolean markAsUncompleted(String userId) {
        if (uncompletedUserIds.contains(userId)) return false;
        uncompletedUserIds.add(userId);
        completedUserIds.remove(userId);
        return true;
    }

    /**
     * 重置用户任务状态
     * @param userId 用户ID
     */
    public void resetUserStatus(String userId) {
        completedUserIds.remove(userId);
        uncompletedUserIds.remove(userId);
    }

    /**
     * 检查用户是否已完成任务
     * @param userId 用户ID
     */
    public boolean isUserCompleted(String userId) {
        return completedUserIds.contains(userId);
    }

    /**
     * 检查用户是否未完成任务
     * @param userId 用户ID
     */
    public boolean isUserUncompleted(String userId) {
        return uncompletedUserIds.contains(userId);
    }
}