package com.WhiteDeer.controller;


import com.WhiteDeer.entity.User;
import com.WhiteDeer.mapper.dto.UserDto;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.mapper.dto.UserDto;
import com.WhiteDeer.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;

import java.util.Set;

@RestController//返回对象，直接转化为json文本
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseMessage createUser(@Validated @RequestBody UserDto userDto) {
        User user = userService.add(userDto);
        return ResponseMessage.success(user);
    }

    @GetMapping("/{userId}")
    public ResponseMessage getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseMessage.success(user);
    }

    @PutMapping
    public ResponseMessage updateUser(@Validated @RequestBody UserDto userDto) {
        User user = userService.update(userDto);
        return ResponseMessage.success(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseMessage deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return ResponseMessage.success();
    }

    @PostMapping("/{userId}/groups/{groupId}")
    public ResponseMessage addUserToGroup(
            @PathVariable String userId,
            @PathVariable String groupId) {
        userService.addUserToGroup(userId, groupId);
        return ResponseMessage.success();
    }

    @DeleteMapping("/{userId}/groups/{groupId}")
    public ResponseMessage removeUserFromGroup(
            @PathVariable String userId,
            @PathVariable String groupId) {
        userService.removeUserFromGroup(userId, groupId);
        return ResponseMessage.success();
    }

    @PostMapping("/{userId}/tasks/{taskId}/complete")
    public ResponseMessage markTaskAsCompleted(
            @PathVariable String userId,
            @PathVariable String taskId) {
        userService.markTaskAsCompleted(userId, taskId);
        return ResponseMessage.success();
    }

    @PostMapping("/{userId}/tasks/{taskId}/incomplete")
    public ResponseMessage markTaskAsNotCompleted(
            @PathVariable String userId,
            @PathVariable String taskId) {
        userService.markTaskAsNotCompleted(userId, taskId);
        return ResponseMessage.success();
    }

    @GetMapping("/{userId}/groups")
    public ResponseMessage getUserGroups(@PathVariable String userId) {
        Set<String> groups = userService.getUserGroups(userId);
        return ResponseMessage.success(groups);
    }

    @GetMapping("/{userId}/completed-tasks")
    public ResponseMessage getCompletedTasks(@PathVariable String userId) {
        Set<String> tasks = userService.getCompletedTasks(userId);
        return ResponseMessage.success(tasks);
    }

    @GetMapping("/{userId}/incomplete-tasks")
    public ResponseMessage getNotCompletedTasks(@PathVariable String userId) {
        Set<String> tasks = userService.getNotCompletedTasks(userId);
        return ResponseMessage.success(tasks);
    }
}