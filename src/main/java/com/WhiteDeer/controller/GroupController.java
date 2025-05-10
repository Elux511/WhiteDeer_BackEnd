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

    @PostMapping
    public ResponseMessage createGroup(@Validated @RequestBody GroupDto groupDto) {
        Group group = groupService.createGroup(groupDto);
        return ResponseMessage.success(group);
    }

    @GetMapping("/{groupId}")
    public ResponseMessage getGroup(@PathVariable String groupId) {
        Group group = groupService.getGroup(groupId);
        return ResponseMessage.success(group);
    }

    @PutMapping
    public ResponseMessage updateGroup(@Validated @RequestBody GroupDto groupDto) {
        Group group = groupService.updateGroup(groupDto);
        return ResponseMessage.success(group);
    }

    @DeleteMapping("/{groupId}")
    public ResponseMessage deleteGroup(@PathVariable String groupId) {
        groupService.deleteGroup(groupId);
        return ResponseMessage.success();
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseMessage addMemberToGroup(
            @PathVariable String groupId,
            @PathVariable String userId,
            @RequestParam(defaultValue = "member") String role) {
        groupService.addMemberToGroup(groupId, userId, role);
        return ResponseMessage.success();
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseMessage removeMemberFromGroup(
            @PathVariable String groupId,
            @PathVariable String userId) {
        groupService.removeMemberFromGroup(groupId, userId);
        return ResponseMessage.success();
    }

    @PostMapping("/{groupId}/tasks/{taskId}/complete")
    public ResponseMessage markTaskAsCompleted(
            @PathVariable String groupId,
            @PathVariable String taskId) {
        groupService.markTaskAsCompleted(groupId, taskId);
        return ResponseMessage.success();
    }

    @PostMapping("/{groupId}/tasks/{taskId}/incomplete")
    public ResponseMessage markTaskAsNotCompleted(
            @PathVariable String groupId,
            @PathVariable String taskId) {
        groupService.markTaskAsNotCompleted(groupId, taskId);
        return ResponseMessage.success();
    }

    @GetMapping("/{groupId}/members")
    public ResponseMessage getGroupMembers(@PathVariable String groupId) {
        Set<GroupMember> members = groupService.getGroupMembers(groupId);
        return ResponseMessage.success(members);
    }

    @GetMapping("/{groupId}/completed-tasks")
    public ResponseMessage getCompletedTasks(@PathVariable String groupId) {
        Set<String> tasks = groupService.getCompletedTasks(groupId);
        return ResponseMessage.success(tasks);
    }

    @GetMapping("/{groupId}/incomplete-tasks")
    public ResponseMessage getNotCompletedTasks(@PathVariable String groupId) {
        Set<String> tasks = groupService.getNotCompletedTasks(groupId);
        return ResponseMessage.success(tasks);
    }
}