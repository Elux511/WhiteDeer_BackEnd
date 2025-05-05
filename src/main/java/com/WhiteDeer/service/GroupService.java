package com.WhiteDeer.service;

import com.WhiteDeer.Group;
import com.WhiteDeer.GroupMember;
import com.WhiteDeer.Task;
import com.WhiteDeer.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalTime;

public class GroupService {
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
}
