package com.WhiteDeer.controller;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.entity.GroupMember;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.mapper.dto.GroupDto;
import com.WhiteDeer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 创建新用户组
     * @param groupDto 包含组信息的传输对象
     * @return 创建成功的组数据
     */
    @PostMapping
    public ResponseMessage createGroup(@Validated @RequestBody GroupDto groupDto) {
        Group group = groupService.createGroup(groupDto);
        return ResponseMessage.success(group);
    }

    /**
     * 获取指定用户组详情
     * @param groupId 用户组ID
     * @return 用户组详细信息
     */
    @GetMapping("/{groupId}")
    public ResponseMessage getGroup(@PathVariable String groupId) {
        Group group = groupService.getGroup(groupId);
        return ResponseMessage.success(group);
    }

    /**
     * 更新用户组信息
     * @param groupDto 包含更新数据的组传输对象
     * @return 更新后的组数据
     */
    @PutMapping
    public ResponseMessage updateGroup(@Validated @RequestBody GroupDto groupDto) {
        Group group = groupService.updateGroup(groupDto);
        return ResponseMessage.success(group);
    }

    /**
     * 删除用户组
     * @param groupId 要删除的组ID
     * @return 空响应（操作状态）
     */
    @DeleteMapping("/{groupId}")
    public ResponseMessage deleteGroup(@PathVariable String groupId) {
        groupService.deleteGroup(groupId);
        return ResponseMessage.success();
    }

    /**
     * 添加用户到组
     * @param groupId 目标组ID
     * @param userId 要添加的用户ID
     * @param role 成员角色（默认"member"）
     * @return 空响应（操作状态）
     */
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseMessage addMemberToGroup(
            @PathVariable String groupId,
            @PathVariable String userId,
            @RequestParam(defaultValue = "member") String role) {
        groupService.addMemberToGroup(groupId, userId, role);
        return ResponseMessage.success();
    }

    /**
     * 从组中移除用户
     * @param groupId 目标组ID
     * @param userId 要移除的用户ID
     * @return 空响应（操作状态）
     */
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseMessage removeMemberFromGroup(
            @PathVariable String groupId,
            @PathVariable String userId) {
        groupService.removeMemberFromGroup(groupId, userId);
        return ResponseMessage.success();
    }

    /**
     * 标记组任务为已完成
     * @param groupId 组ID
     * @param taskId 任务ID
     * @return 空响应（操作状态）
     */
    @PostMapping("/{groupId}/tasks/{taskId}/complete")
    public ResponseMessage markTaskAsCompleted(
            @PathVariable String groupId,
            @PathVariable String taskId) {
        groupService.markTaskAsCompleted(groupId, taskId);
        return ResponseMessage.success();
    }

    /**
     * 标记组任务为未完成
     * @param groupId 组ID
     * @param taskId 任务ID
     * @return 空响应（操作状态）
     */
    @PostMapping("/{groupId}/tasks/{taskId}/incomplete")
    public ResponseMessage markTaskAsNotCompleted(
            @PathVariable String groupId,
            @PathVariable String taskId) {
        groupService.markTaskAsNotCompleted(groupId, taskId);
        return ResponseMessage.success();
    }

    /**
     * 获取组成员列表
     * @param groupId 组ID
     * @return 组成员信息集合
     */
    @GetMapping("/{groupId}/members")
    public ResponseMessage getGroupMembers(@PathVariable String groupId) {
        Set<GroupMember> members = groupService.getGroupMembers(groupId);
        return ResponseMessage.success(members);
    }

    /**
     * 获取组已完成任务列表
     * @param groupId 组ID
     * @return 已完成任务ID集合
     */
    @GetMapping("/{groupId}/completed-tasks")
    public ResponseMessage getCompletedTasks(@PathVariable String groupId) {
        Set<String> tasks = groupService.getCompletedTasks(groupId);
        return ResponseMessage.success(tasks);
    }

    /**
     * 获取组未完成任务列表
     * @param groupId 组ID
     * @return 未完成任务ID集合
     */
    @GetMapping("/{groupId}/incomplete-tasks")
    public ResponseMessage getNotCompletedTasks(@PathVariable String groupId) {
        Set<String> tasks = groupService.getNotCompletedTasks(groupId);
        return ResponseMessage.success(tasks);
    }
}