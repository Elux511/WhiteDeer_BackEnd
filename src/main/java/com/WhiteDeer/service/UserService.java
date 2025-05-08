package com.WhiteDeer.service;

import com.WhiteDeer.Group;
import com.WhiteDeer.Task;
import com.WhiteDeer.User;
import com.WhiteDeer.mapper.dto.TaskDto;
import com.WhiteDeer.mapper.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements UserServiceImpl{
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


    @Autowired
    private TaskService userService;
    @Override
    public User add(UserDto user){
        User newuser = new User();
        BeanUtils.copyProperties(user,newuser);
        return userRepository.save(newuser);//插入和修改调用save
    }


    @Override
    public Task getUser(UserDto user){
        return userRepository.findById(user.getUser_id()).orElseThrow(()->{
            return new IllegalArgumentException("用户不存在");
        });
        return null;
    }

    @Override
    public User edit(UserDto user){
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        return userRepository.save(user1);
    }

    @Override
    public void delete(Integer taskId){
        return userRepository.deleteBYId(user.getId());
    }

}
