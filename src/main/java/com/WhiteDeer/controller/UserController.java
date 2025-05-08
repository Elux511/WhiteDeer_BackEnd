package com.WhiteDeer.controller;


import com.WhiteDeer.User;
import com.WhiteDeer.mapper.dto.UserDto;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;


@RestController//返回对象，直接转化为json文本
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    //增加打卡任务
    @PostMapping
    public String add(@Validated @RequestBody UserDto user) {
        User user1=userService.add(user);
        return ResponseMessage.success(user1);
    }
    //查询打卡任务
    @GetMapping("/{userId}")
    public ResponseMessage get(@PathVariable Integer userId){
        User userNew=userService.getTask(userId);
        return ResponseMessage.success(userNew);
    }
    //修改
    @PutMapping
    public ResponseMessage edit(@PathVariable Integer userId){
        User userNew=userService.edit(userId);
        return ResponseMessage.success(userNew);
    }
    //删除
    @DeleteMapping("/{userId}")
    public ResponseMessage delete(@PathVariable Integer userId){
        userService.delete(userId);
        return ResponseMessage.success();
    }

}
