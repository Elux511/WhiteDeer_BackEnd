package com.WhiteDeer.controller;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.mapper.dto.GroupDto;
import com.WhiteDeer.mapper.ResponseMessage;
import com.WhiteDeer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController//返回对象，直接转化为json文本
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;
    @Autowired
    private Group group;


    //增加打卡任务
    @PostMapping
    public String add(@Validated @RequestBody GroupDto group) {
        Group groupNew=groupService.add(group);
        return ResponseMessage.success(groupNew);
    }
    //查询打卡任务
    @GetMapping("/{groupId}")
    public ResponseMessage get(@PathVariable Integer groupId){
        Group groupNew=groupService.getGroup(groupId);
        return ResponseMessage.success(groupNew);
    }
    //修改
    @PutMapping
    public ResponseMessage edit(@PathVariable Integer groupId){
        Group groupNew=groupService.edit(groupId);
        return ResponseMessage.success(groupNew);
    }
    //删除
    @DeleteMapping("/{groupId}")
    public ResponseMessage delete(@PathVariable Integer groupId){
        groupService.delete(groupId);
        return ResponseMessage.success();
    }

}
