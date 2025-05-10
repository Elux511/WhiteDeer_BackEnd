package com.WhiteDeer.mapper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
    private String id;

    private String name;

    private String password;

    private String phone_number;
    private byte[] face_image;
    private String face_image_content_type;
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
        return phone_number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }
    // 新增人脸图片相关方法
    public byte[] getFaceImage() {
        return face_image;
    }

    public void setFaceImage(byte[] faceImage) {
        this.face_image = faceImage;
    }

    public String getFaceImageContentType() {
        return face_image_content_type;
    }

    public void setFaceImageContentType(String faceImageContentType) {
        this.face_image_content_type = faceImageContentType;
    }
}