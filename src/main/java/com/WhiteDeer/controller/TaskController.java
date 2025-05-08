package com.WhiteDeer.controller;

import com.WhiteDeer.Task;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.service.TaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;


@RestController//返回对象，直接转化为json文本
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;


    //增加打卡任务
    @PostMapping
    public String add(@Validated @RequestBody TaskDTO task) {
        Task taskNew=taskService.add(task);
        return ResponseMessage.success(taskNew);
    }
    //查询打卡任务
    @GetMapping("/{taskId}")
    public ResponseMessage get(@PathVariable Integer taskId){
        Task taskNew=taskService.getTask(taskId);
        return ResponseMessage.success(taskNew);
    }
    //修改
    @PutMapping
    public ResponseMessage edit(@PathVariable Integer taskId){
        Task taskNew=taskService.edit(taskId);
        return ResponseMessage.success(taskNew);
    }
    //删除
    @DeleteMapping("/{taskId}")
    public ResponseMessage delete(@PathVariable Integer taskId){
        taskService.delete(taskId);
        return ResponseMessage.success();
    }

}
