package com.WhiteDeer;

import java.time.LocalTime;
import java.util.Vector;

public class Task {
    public enum CheckInMethod{
        faceRecognition,
        geoFencing,
        both
    };

    private String id;
    private String name;
    private Group group;
    //QRcode
    private LocalTime begin_time;
    private LocalTime end_time;
    private CheckInMethod method;
    private Vector<String> yes_user;
    private Vector<String> no_user;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Group getGroup() {
        return group;
    }
    public LocalTime getBegin_time() {
        return begin_time;
    }
    public LocalTime getEnd_time() {
        return end_time;
    }
    public CheckInMethod getMethod() {
        return method;
    }
    public Vector<String> getYes_user() {
        return yes_user;
    }
    public Vector<String> getNo_user() {
        return no_user;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setQRcode(){

    }
    public void setBeginTime(LocalTime begin_time){
        this.begin_time = begin_time;
    }
    public void setEndTime(LocalTime end_time){
        this.end_time = end_time;
    }
    public void addYes(String user_id){
        yes_user.add(user_id);
    }
    public void deleteYes(String user_id){
        yes_user.remove(user_id);
    }
    public void addNo(String user_id){
        no_user.add(user_id);
    }
    public void deleteNo(String user_id){
        no_user.remove(user_id);
    }
}
