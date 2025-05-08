package com.WhiteDeer.service;
import com.WhiteDeer.mapper.dto.GroupDto;
import com.WhiteDeer.Group;
import com.WhiteDeer.GroupMember;
import com.WhiteDeer.Task;
import com.WhiteDeer.User;
import com.WhiteDeer.mapper.dto.TaskDto;
import org.apache.catalina.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalTime;

public class GroupService implements GroupServiceImpl{
    Group group;
    public void setGroup(Group group){
        this.group = group;
    }
    public void setName(String name){
        group.setName(name);
    }
    public void setIntroduction(String introduction){
        group.setIntroduction(introduction);
    }
    public void addMember(User user){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        GroupMember member = (GroupMember) ctx.getBean("groupMember");
        member.setUser(user);
        member.setGroup(group);
        member.setMember_type(GroupMember.Type.member);
        member.setJoin_time(LocalTime.now());
        group.addMember(member);
    }
    public void deleteMember(User user){
        for(GroupMember gm : group.getMember_list())
        {
            if (gm.getUser() == user)
                group.deleteMember(gm);
        }
    }
    public void addYes(Task task){
        group.addYes(task.getId());
    }
    public void deleteYes(Task task){
        group.deleteYes(task.getId());
    }
    public void addNo(Task task){
        group.addNo(task.getId());
    }
    public void deleteNo(Task task){
        group.deleteNo(task.getId());
    }


    @Autowired
    private GroupService groupService;
    @Override
    public Group add(GroupDto group){
        Group group1 = new Group();
        BeanUtils.copyProperties(group,group1);
        Store groupRepository;
        return groupRepository.save(group1);//插入和修改调用save
    }

    @Override
    public Group getGroup(GroupDto group){
        return groupRepository.findById(group.getGroupId()).orElseThrow(()->{
            return new IllegalArgumentException("用户不存在");
        });
        return null;
    }

    @Override
    public Group edit(GroupDto group){
        Group group1 = new Group();
        BeanUtils.copyProperties(group,group1);
        return groupRepository.save(group1);
    }

    @Override
    public void delete(Integer groupId){
        return groupRepository.deleteBYId(groupId);
    }


}
