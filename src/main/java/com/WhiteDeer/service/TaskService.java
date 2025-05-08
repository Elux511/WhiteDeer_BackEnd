//打卡任务类
package com.WhiteDeer.service;

import com.WhiteDeer.Task;
import com.WhiteDeer.User;
import com.WhiteDeer.repository.TaskRepository;
import com.WhiteDeer.mapper.dto.TaskDto;
import org.apache.catalina.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalTime;
@Service
public abstract   class TaskService implements TaskServiceImpl{
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
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Task add(TaskDto task){
        Task task1 = new Task();
        BeanUtils.copyProperties(task,task1);
        return taskRepository.save(task1);//插入和修改调用save
    }

    @Override
    public Task getTask(TaskDto task){
        return taskRepository.findById(Integer.parseInt(task.getTaskId())).orElseThrow(()->{
            return new IllegalArgumentException("用户不存在");
        });
    }

    @Override
    public Task edit(TaskDto task){
        Task task1 = new Task();
        BeanUtils.copyProperties(task,task1);
        return taskRepository.save(task1);
    }

    @Override
    public void delete(Integer taskId){
        return taskRepository.deleteBYId(taskId);
    }

}
