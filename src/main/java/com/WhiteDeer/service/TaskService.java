package com.WhiteDeer.controller;

import com.WhiteDeer.Task;
import com.WhiteDeer.mapper.dto.TaskDto;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskService {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseMessage add(@Validated @RequestBody TaskDto taskDto) {
        Task task = taskService.add(taskDto);
        return ResponseMessage.success(task);
    }

    @GetMapping("/{taskId}")
    public ResponseMessage get(@PathVariable Integer taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseMessage.success(task);
    }

    @PutMapping
    public ResponseMessage edit(@Validated @RequestBody TaskDto taskDto) {
        Task task = taskService.edit(taskDto);
        return ResponseMessage.success(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseMessage delete(@PathVariable Integer taskId) {
        taskService.delete(taskId);
        return ResponseMessage.success();
    }
}