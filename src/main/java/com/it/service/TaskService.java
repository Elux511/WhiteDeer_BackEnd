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
    public void setBeginTime(LocalTime beginTime){
        task.setBeginTime(beginTime);
    }
    public void setEndTime(LocalTime endTime){
        task.setEndTime(endTime);
    }
    public void addYes(Task task){
        task.addYes(task.getId());
    }
    public void deleteYes(Task task){
        task.deleteYes(task.getId());
    }
    public void addNo(Task task){
        task.addNo(task.getId());
    }
    public void deleteNo(Task task){
        task.deleteNo(task.getId());
    }
    public void finish(User user){
        user.addYes(user.getId());
        user.deleteNo(user.getId());
    }
}
