package com.it.mapper;

import ch.qos.logback.core.BasicStatusManager;
import jakarta.persistence.*;

@Table(name="table_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer User_id;
    @Column(name="user_name")
    private String user_name;
    @Column(name="password")
    private String password;
    @Column(name="phone_number")
    private String phone_number;
    @Column(name="face")
    private String face;
    @Column(name="manage_group")
    private String groupcontrol_set;
    @Column(name="join_group")
    private String groupmember_name;

    //User_id
    public Integer getUser_id() {
        return User_id;
    }
    public void setUser_id(Integer user_id) {
        this.User_id = user_id;
    }

    //User_name
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    //face
    public String getFace() {
        return face;
    }
    public void setFace(String face) {
        this.face = face;
    }

    //password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //groupcontrol_set
    public void setGroupcontrol_set(String groupcontrol_set) {
        this.groupcontrol_set = groupcontrol_set;
    }
    public String getGroupmember_name() {
        return groupmember_name;
    }

    //groupmember_set
    public void setGroupmember_name(String groupmember_name) {
        this.groupmember_name = groupmember_name;
    }

    public String getGroupcontrol_set() {
        return groupcontrol_set;
    }

    //phone_number

    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
