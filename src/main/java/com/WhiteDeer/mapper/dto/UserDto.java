package com.WhiteDeer.mapper.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;

public class UserDto {
    private Integer User_id;
    private String user_name;
    private String password;
    private String phone_number;
    private String face;
    private String groupcontrol_set;
    private String groupmember_name;


    public Integer getUser_id() {
        return User_id;
    }

    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getGroupcontrol_set() {
        return groupcontrol_set;
    }

    public void setGroupcontrol_set(String groupcontrol_set) {
        this.groupcontrol_set = groupcontrol_set;
    }

    public String getGroupmember_name() {
        return groupmember_name;
    }

    public void setGroupmember_name(String groupmember_name) {
        this.groupmember_name = groupmember_name;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "User_id=" + User_id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", face='" + face + '\'' +
                ", groupcontrol_set='" + groupcontrol_set + '\'' +
                ", groupmember_name='" + groupmember_name + '\'' +
                '}';
    }
}
