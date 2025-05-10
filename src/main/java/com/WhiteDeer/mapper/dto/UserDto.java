package com.WhiteDeer.mapper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
    private String id;

    private String name;

    private String password;

    private String phoneNumber;
    private byte[] faceImage;
    private String faceImageContentType;
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    // 新增人脸图片相关方法
    public byte[] getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(byte[] faceImage) {
        this.faceImage = faceImage;
    }

    public String getFaceImageContentType() {
        return faceImageContentType;
    }

    public void setFaceImageContentType(String faceImageContentType) {
        this.faceImageContentType = faceImageContentType;
    }
}