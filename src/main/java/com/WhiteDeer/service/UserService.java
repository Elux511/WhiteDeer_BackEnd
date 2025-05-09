package com.WhiteDeer.service;

import com.WhiteDeer.Group;
import com.WhiteDeer.GroupMember;
import com.WhiteDeer.Task;
import com.WhiteDeer.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalTime;

public class UserService {
    User user;
    public void setUser(User user){
        this.user = user;
    }
    public void setName(String userName){
        user.setName(userName);
    }
    public void setPassword(String password){
        user.setPassword(password);
    }
    public void setPhoneNumber(String phone_number){
        user.setPhoneNumber(phone_number);
    }
    public void setFace(){

    }
    public void addGroup(Group group){
        user.addGroup(group.getId());
    }
    public void deleteGroup(Group group){
        user.deleteGroup(group.getId());
    }
    public void addYes(Task task){
        user.addYes(task.getId());
    }
    public void deleteYes(Task task){
        user.deleteYes(task.getId());
    }
    public void addNo(Task task){
        user.addNo(task.getId());
    }
    public void deleteNo(Task task){
        user.deleteNo(task.getId());
    }

    //创建一个团队
    public void createGroup(String group_name, User user, String introduction){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Group group = (Group)ctx.getBean("Group");
        //group.setId();
        group.setName(group_name);
        group.setIntroduction(introduction);

        //把自己加进团队成员中
        GroupMember member = (GroupMember) ctx.getBean("groupMember");
        member.setUser(user);
        member.setGroup(group);
        member.setMember_type(GroupMember.Type.member);
        member.setJoin_time(LocalTime.now());
        group.addMember(member);

        //把团队加进自己所在团队列表
        user.addGroup(group.getId());
    }
}
