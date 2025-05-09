package com.WhiteDeer.mapper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GroupDto {
    private String id;

    @NotBlank(message = "群组名称不能为空")
    @Size(min = 2, max = 50, message = "群组名称长度必须在2-50个字符之间")
    private String name;

    @NotBlank(message = "创建者ID不能为空")
    private String creatorId;

    @Size(max = 500, message = "群组介绍不能超过500个字符")
    private String introduction;

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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}