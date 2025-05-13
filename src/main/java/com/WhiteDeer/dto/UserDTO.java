package com.WhiteDeer.dto;

import jakarta.persistence.Column;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Vector;

public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String phoneNumber;
    private Blob face;
    private Vector<Long> yesTaskSet;
    private Vector<Long> noTaskSet;
    private Vector<Long> createGroupSet;
    private Vector<Long> joinGroupSet;


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

    public Vector<Long> getYesTaskSet() {
        return yesTaskSet;
    }
    public void setYesTaskSet(Vector<Long> yesTaskSet) {
        this.yesTaskSet = yesTaskSet;
    }

    public Vector<Long> getJoinGroupSet() {
        return joinGroupSet;
    }
    public void setJoinGroupSet(Vector<Long> joinGroupSet) {
        this.joinGroupSet = joinGroupSet;
    }

    public Vector<Long> getCreateGroupSet() {
        return createGroupSet;
    }
    public void setCreateGroupSet(Vector<Long> createGroupSet) {
        this.createGroupSet = createGroupSet;
    }

    public Vector<Long> getNoTaskSet() {
        return noTaskSet;
    }
    public void setNoTaskSet(Vector<Long> noTaskSet) {
        this.noTaskSet = noTaskSet;
    }

    public boolean isHaveface() {
        try {
            return face != null && face.length() > 0;
        } catch (SQLException e) {
            return false;
        }
    }//可能有优化空间

}
