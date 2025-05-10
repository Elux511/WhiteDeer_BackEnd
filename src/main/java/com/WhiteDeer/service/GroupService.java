package com.WhiteDeer.service;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.entity.GroupMember;
import com.WhiteDeer.exception.GroupNotFoundException;
import com.WhiteDeer.mapper.dto.GroupDto;
import java.util.Set;

/**
 * 群组服务接口
 */
public interface GroupService {

    /**
     * 创建群组
     * @param groupDto 群组数据传输对象
     * @return 创建的群组实体
     */
    Group createGroup(GroupDto groupDto);

    /**
     * 获取群组信息
     * @param groupId 群组ID
     * @return 群组实体
     * @throws GroupNotFoundException 当群组不存在时抛出
     */
    Group getGroup(String groupId) throws GroupNotFoundException;

    /**
     * 更新群组信息
     * @param groupDto 群组数据传输对象
     * @return 更新后的群组实体
     */
    Group updateGroup(GroupDto groupDto);

    /**
     * 删除群组
     * @param groupId 群组ID
     */
    void deleteGroup(String groupId);

    /**
     * 添加成员到群组
     * @param groupId 群组ID
     * @param userId 用户ID
     * @param role 成员角色
     */
    void addMemberToGroup(String groupId, String userId, String role);

    /**
     * 从群组移除成员
     * @param groupId 群组ID
     * @param userId 用户ID
     */
    void removeMemberFromGroup(String groupId, String userId);

    /**
     * 标记任务为已完成
     * @param groupId 群组ID
     * @param taskId 任务ID
     */
    void markTaskAsCompleted(String groupId, String taskId);

    /**
     * 标记任务为未完成
     * @param groupId 群组ID
     * @param taskId 任务ID
     */
    void markTaskAsNotCompleted(String groupId, String taskId);

    /**
     * 获取群组成员列表
     * @param groupId 群组ID
     * @return 成员集合
     */
    Set<GroupMember> getGroupMembers(String groupId);

    /**
     * 获取群组已完成任务列表
     * @param groupId 群组ID
     * @return 任务ID集合
     */
    Set<String> getCompletedTasks(String groupId);

    /**
     * 获取群组未完成任务列表
     * @param groupId 群组ID
     * @return 任务ID集合
     */
    Set<String> getNotCompletedTasks(String groupId);
}