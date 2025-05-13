package com.WhiteDeer.dao;


import jakarta.persistence.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Vector;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增标记
    private long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "face")
    private Blob face;

    @Column(name = "yes_task_set")
    private Vector<Long> yesTaskSet;

    @Column(name = "no_task_set")
    private Vector<Long> noTaskSet;

    @Column(name = "create_group_set")
    private Vector<Long> createGroupSet;

    @Column(name = "join_group_set")
    private Vector<Long> joinGroupSet;

    public long getId() {
        return id;
    }
    public void setId(long id) {
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

    public Vector<Long> getJoinGroupSet() {
        return joinGroupSet;
    }
    public void setJoinGroupSet(Vector<Long> joinGroupSet) {
        this.joinGroupSet = joinGroupSet;
    }

    public Vector<Long> getYesTaskSet() {
        return yesTaskSet;
    }
    public void setYesTaskSet(Vector<Long> yesTaskSet) {
        this.yesTaskSet = yesTaskSet;
    }

    public Vector<Long> getNoTaskSet() {
        return noTaskSet;
    }
    public void setNoTaskSet(Vector<Long> noTaskSet) {
        this.noTaskSet = noTaskSet;
    }

    public Vector<Long> getCreateGroupSet() {
        return createGroupSet;
    }
    public void setCreateGroupSet(Vector<Long> createGroupSet) {
        this.createGroupSet = createGroupSet;
    }

    public boolean isHaveface() {
        try {
            return face != null && face.length() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}


