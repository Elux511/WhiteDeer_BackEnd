//打卡任务类
package com.it.service;

import com.it.Task;
import com.it.User;

import java.time.LocalTime;

public class TaskService {
    Task task;
    public void setTask(Task task){
        this.task = task;
    }
    public void setName(String name){
        task.setName(name);
    }
    public void setQRcode(){

    }
    public void setBeginTime(LocalTime begin_time){
        task.setBeginTime(begin_time);
    }
    public void setEndTime(LocalTime end_time){
        task.setEndTime(end_time);
    }
    public  void addYes(User user){
        task.addYes(user.getId());
    }
    public void deleteYes(User user){
        task.deleteYes(user.getId());
    }
    public void addNo(User user){
        task.addNo(user.getId());
    }
    public void deleteNo(User user){
        task.deleteNo(user.getId());
    }
    public void finish(User user){
        user.addYes(user.getId());
        user.deleteNo(user.getId());
    }
}
