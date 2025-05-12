package com.WhiteDeer.dao;


import jakarta.persistence.*;

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
    private byte[] face;

    @Column(name = "yes_task_set")
    private Vector<String> yesTaskSet;

    @Column(name = "no_task_set")
    private Vector<String> noTaskSet;

    @Column(name = "create_group_set")
    private Vector<String> createGroupSet;

    @Column(name = "join_group_set")
    private Vector<String> joinGroupSet;

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

    public byte[] getFace() {
        return face;
    }
    public void setFace(byte[] face) {
        this.face = face;
    }

    public Vector<String> getYesTaskSet() {
        return yesTaskSet;
    }
    public void setYesTaskSet(Vector<String> yesTaskSet) {
        this.yesTaskSet = yesTaskSet;
    }

    public Vector<String> getNoTaskSet() {
        return noTaskSet;
    }
    public void setNoTaskSet(Vector<String> noTaskSet) {
        this.noTaskSet = noTaskSet;
    }

    public Vector<String> getCreateGroupSet() {
        return createGroupSet;
    }
    public void setCreateGroupSet(Vector<String> createGroupSet) {
        this.createGroupSet = createGroupSet;
    }

    public Vector<String> getJoinGroupSet() {
        return joinGroupSet;
    }
    public void setJoinGroupSet(Vector<String> joinGroupSet) {
        this.joinGroupSet = joinGroupSet;
    }
}


