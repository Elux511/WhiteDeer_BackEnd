//打卡任务类
package com.WhiteDeer.service;

import com.WhiteDeer.Task;
import com.WhiteDeer.User;


import java.time.LocalTime;
import java.util.Vector;

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
    public void addYes(User user){
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
        user.addYes(user.getId());
        user.deleteNo(user.getId());
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
