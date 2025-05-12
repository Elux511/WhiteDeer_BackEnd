package com.WhiteDeer.dto;

import jakarta.persistence.Column;

import java.sql.Blob;
import java.util.Vector;

public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String phoneNumber;
    private Blob face;
    private Vector<String> yesTaskSet;
    private Vector<String> noTaskSet;
    private Vector<String> createGroupSet;
    private Vector<String> joinGroupSet;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Blob getFace() {
        return face;
    }
    public void setFace(Blob face) {
        this.face = face;
    }

    public Vector<String> getYesTaskSet() {
        return yesTaskSet;
    }
    public void setYesTaskSet(Vector<String> yesTaskSet) {
        this.yesTaskSet = yesTaskSet;
    }

    public Vector<String> getJoinGroupSet() {
        return joinGroupSet;
    }
    public void setJoinGroupSet(Vector<String> joinGroupSet) {
        this.joinGroupSet = joinGroupSet;
    }

    public Vector<String> getCreateGroupSet() {
        return createGroupSet;
    }
    public void setCreateGroupSet(Vector<String> createGroupSet) {
        this.createGroupSet = createGroupSet;
    }

    public Vector<String> getNoTaskSet() {
        return noTaskSet;
    }
    public void setNoTaskSet(Vector<String> noTaskSet) {
        this.noTaskSet = noTaskSet;
    }
}
