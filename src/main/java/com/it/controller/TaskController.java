package com.it.controller;

import com.it.service.TaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;


@RestController
@RequestMapping("/Task")
public class TaskController {
    @Autowired
    private TaskService taskService;


    //增加打卡任务
    @PostMapping
    public String add(@RequestBody TaskDTO task) {
        taskService.add(task);
        return "Task added";
    }
    //查询打卡任务
    //@GetMapping
    //修改
    //@PutMapping
    //删除
    //@DeleteMapping

}
