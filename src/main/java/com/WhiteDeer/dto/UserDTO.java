package com.WhiteDeer.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String phoneNumber;
    private byte[] face;



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

    public byte[] getFace() {
        return face;
    }
    public void setFace(byte[] face) {
        this.face = face;
    }
}
