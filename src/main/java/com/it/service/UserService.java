package com.it.service;

import com.it.Group;
import com.it.Task;
import com.it.User;

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
}
