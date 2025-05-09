//打卡任务类
package com.WhiteDeer.service;

import com.WhiteDeer.entity.Task;
import com.WhiteDeer.entity.User;
import com.WhiteDeer.mapper.dto.TaskDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Vector;

@Service
public class TaskService implements TaskServiceImpl{
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
    @Override
    public Task add(TaskDto task){
        Task task1 = new Task();
        BeanUtils.copyProperties(task,task1);
        return taskRepository.save(task1);//插入和修改调用save
    }

    @Override
    public Task getTask(TaskDto task){
        return taskRepository.findById(taskId).orElseThrow(()->{
            return new IllegalArgumentException("用户不存在");
        });
        return null;
    }

    @Override
    public Task edit(TaskUto task){
        Task task1 = new Task();
        BeanUtils.copyProperties(task,task1);
        return taskRepository.save(task1);
    }

    @Override
    public void delete(Integer taskId){
        return taskRepository.deleteBYId(taskId);
    }
    //用于faceRecognition型打卡任务的检测
    public void checkIn(User user, String img_path){
        if(PyAPI.faceRecognition(user.getId(), img_path))
            finish(user);
    }
    //用于geoFencing型打卡任务的检测
    public void checkIn(User user, double lat, double lng){
        Vector<Double> location = PyAPI.geoFencing();
        Double lat_ = location.get(0);
        Double lng_ = location.get(1);
        if(calculateDistance(lat_,lng_,lat,lng) < task.getMistakeRange())
            finish(user);
    }
    //用于all型打卡任务的检测
    public void checkIn(User user, String img_path,double lat, double lng){
        boolean flag;
        Vector<Double> location = PyAPI.geoFencing();
        Double lat_ = location.get(0);
        Double lng_ = location.get(1);
        flag = PyAPI.faceRecognition(user.getId(), img_path) && (calculateDistance(lat_,lng_,lat,lng) < task.getMistakeRange());
        if (flag)
            finish(user);
    }
    //完成打卡任务
    public void finish(User user){
        //用户的任务列表里对该任务进行增删
        user.addYes(task.getId());
        user.deleteNo(task.getId());
        //任务的用户列表里对该用户进行增删
        task.addYes(user.getId());
        task.deleteNo(user.getId());
    }


    //计算经纬度距离
    public double calculateDistance(Double lat1, Double lng1, double lat2, double lng2){
        final double R = 6371000; // 地球平均半径（公里）

        // 将角度转换为弧度
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lng1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lng2);

        // 纬度差和经度差
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine公式计算
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
