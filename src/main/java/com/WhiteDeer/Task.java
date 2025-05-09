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
    private String introduction;
    //QRcode
    private LocalTime begin_time;
    private LocalTime end_time;
    private double longitude;//经度
    private double latitude;//纬度
    private double mistake_range;//位置误差
    private CheckInMethod method;
    private Vector<String> yes_user;
    private Vector<String> no_user;

    //getter
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Group getGroup() {
        return group;
    }
    public String getIntroduction() {
        return introduction;
    }
    //getQRcode
    public LocalTime getBegin_time() {
        return begin_time;
    }
    public LocalTime getEnd_time() {
        return end_time;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getMistakeRange() {
        return mistake_range;
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

    //setter
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setGroup(Group group){
        this.group = group;
    }
    public void setIntroduction(String introduction){
        this.introduction = introduction;
    }
    public void setQRcode(){

    }
    public void setBeginTime(LocalTime begin_time){
        this.begin_time = begin_time;
    }
    public void setEndTime(LocalTime end_time){
        this.end_time = end_time;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setMistakeRange(double mistake_range) {
        this.mistake_range = mistake_range;
    }
    public void setMethod(CheckInMethod method){
        this.method = method;
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
