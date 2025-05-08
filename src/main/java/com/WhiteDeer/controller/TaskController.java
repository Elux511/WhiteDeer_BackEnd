package com.WhiteDeer.controller;

import com.WhiteDeer.Task;
import com.WhiteDeer.mapper.dto.TaskDto;
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

    @Autowired
    ResponseMessage responseMessage;
    //增加打卡任务
    @PostMapping
    public String add(@Validated @RequestBody TaskDto task) {
        Task taskNew=taskService.add(task);
        return ResponseMessage.success();
    }
    //查询打卡任务
    @GetMapping("/{taskId}")
    public ResponseMessage get(@PathVariable Integer taskId){
        //TaskDto task=new TaskDto();
        Task taskNew=taskService.getTask(taskid);
        return ResponseMessage.success(taskNew);
    }
    //修改
    @PutMapping
    public ResponseMessage edit(@PathVariable TaskDto taskId){
        Task taskNew=taskService.edit(taskId);
        return ResponseMessage.success(taskNew);
    }
    //删除
    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Integer taskId){
        taskService.delete(taskId);
        //success无返回值
    }

}
