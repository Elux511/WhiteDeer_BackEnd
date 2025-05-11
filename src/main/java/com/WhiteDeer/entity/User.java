package com.WhiteDeer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone_number;
    private String password;
    private Set<String> groupSet = new HashSet<>();
    private Set<String> yesTaskSet = new HashSet<>();
    private Set<String> noTaskSet = new HashSet<>();
    @Lob // 大对象注解
    @Column(name = "face_image", columnDefinition = "LONGBLOB") // MySQL的二进制大对象类型
    private byte[] faceImage;

    @Column(name = "face_image_content_type", length = 100)
    private String faceImageContentType;




    //public User() {}

    public User( String name, String phoneNumber, String password, Set<String> groupSet, Set<String> yesTaskSet, Set<String> noTaskSet) {
        this.name = name;
        this.phone_number = phoneNumber;
        this.password = password;
        this.groupSet = groupSet;
        this.yesTaskSet = yesTaskSet;
        this.noTaskSet = noTaskSet;
    }

    // 密码加密存储
    public void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("密码长度不能少于4位");
        }
        // 实际应用中应加密存储密码
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.matches("^1[3-9]\\d{9}$")) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
        this.phone_number = phoneNumber;
    }

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

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getGroupSet() {
        return new HashSet<>(groupSet);
    }

    public Set<String> getYesTaskSet() {
        return new HashSet<>(yesTaskSet);
    }

    public Set<String> getNoTaskSet() {
        return new HashSet<>(noTaskSet);
    }

    // 操作方法

    public void addYesTask(String taskId) {
        yesTaskSet.add(taskId);
        noTaskSet.remove(taskId);
    }

    public void removeYesTask(String taskId) {
        yesTaskSet.remove(taskId);
    }

    public void addNoTask(String taskId) {
        noTaskSet.add(taskId);
        yesTaskSet.remove(taskId);
    }

    public void removeNoTask(String taskId) {
        noTaskSet.remove(taskId);
    }

    public boolean isInGroup(String groupId) {
        return groupSet.contains(groupId);
    }

    public boolean hasCompletedTask(String taskId) {
        return yesTaskSet.contains(taskId);
    }

    public boolean hasNotCompletedTask(String taskId) {
        return noTaskSet.contains(taskId);
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