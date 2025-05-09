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
    //创建一个group_member并初始化，加入到set中
    public void addMember(User user){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        GroupMember member = (GroupMember) ctx.getBean("groupMember");
        member.setUser(user);
        member.setGroup(group);
        member.setMember_type(GroupMember.Type.member);
        member.setJoin_time(LocalTime.now());
        group.addMember(member);
    }
    //遍历group_member
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

    //需要地理定位的打卡
    public void publishTask(String task_name, String introduction, LocalTime begin_time, LocalTime end_time, Task.CheckInMethod method, double lat, double lng, double mistake_range){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Task task = (Task) ctx.getBean("task");
        //task.setId();
        task.setName(task_name);
        task.setIntroduction(introduction);
        task.setBeginTime(begin_time);
        task.setEndTime(end_time);
        task.setMethod(method);
        task.setLatitude(lat);
        task.setLongitude(lng);
        task.setMistakeRange(mistake_range);
        for(GroupMember gm : group.getMember_list())   //将所有团队成员加入至未完成列表
            task.addNo(gm.getUser().getId());
        group.addNo(task.getId());//最终将任务添加至团队未完成列表
    }

    //不需要地理定位的打卡
    public void publishTask(String task_name, String introduction, LocalTime begin_time, LocalTime end_time, Task.CheckInMethod method) {
        publishTask(task_name, introduction, begin_time, end_time, method, -1, -1, -1);
    }
}
