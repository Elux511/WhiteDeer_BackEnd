package com.it;

import java.util.Vector;

public class User {
    private String id;
    private String name;
    private String phone_number;
    private String password;
    private Vector<String> group_set;
    private Vector<String> yes_task_set;
    private Vector<String> no_task_set;
    //face
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getPassword() {
        return password;
    }
    public Vector<String> getGroup_set() {
        return group_set;
    }
    public Vector<String> getYes_task_set() {
        return yes_task_set;
    }
    public Vector<String> getNo_task_set() {
        return no_task_set;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String user_name){
        this.name = user_name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }
    public void setFace(){

    }
    public void addGroup(String group_id){
        group_set.add(group_id);
    }
    public void deleteGroup(String group_id){
        group_set.remove(group_id);
    }
    public void addYes(String task_id){
        yes_task_set.add(task_id);
    }
    public void deleteYes(String task_id){
        yes_task_set.remove(task_id);
    }
    public void addNo(String task_id){
        no_task_set.add(task_id);
    }
    public void deleteNo(String task_id){
        no_task_set.remove(task_id);
    }
}
