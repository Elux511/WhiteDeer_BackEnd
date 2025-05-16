package com.WhiteDeer.converter;

import com.WhiteDeer.dao.GroupInfo;
import com.WhiteDeer.dto.GroupInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

public class GroupInfoConverter {

    public static GroupInfoDTO convertToDTO(GroupInfo groupInfo) {
        GroupInfoDTO dto = new GroupInfoDTO();
        dto.setGroupId(groupInfo.getGroupId());
        dto.setGroupName(groupInfo.getGroupName());
        dto.setGroupIntroduction(groupInfo.getGroupIntroduction());
        dto.setMemberList(groupInfo.getMemberList());
        dto.setYesTaskSet(groupInfo.getYesTaskSet());
        dto.setNoTaskSet(groupInfo.getNoTaskSet());
        dto.setCreatorId(groupInfo.getCreatorId());
        dto.setCreateTime(groupInfo.getCreateTime().toString());

        List<Long> members = groupInfo.getMemberList();
        dto.setMemberCount(members != null ? members.size() : 0);
        dto.setIsFull(dto.getMemberCount() >= groupInfo.getMaxMember() ? "是" : "否");
        return dto;
    }

    public static GroupInfo convertToEntity(GroupInfoDTO groupInfoDTO) {
        GroupInfo entity = new GroupInfo();
        entity.setGroupId(groupInfoDTO.getGroupId());
        entity.setGroupName(groupInfoDTO.getGroupName());
        entity.setGroupIntroduction(groupInfoDTO.getGroupIntroduction());
        entity.setMemberList(groupInfoDTO.getMemberList());
        entity.setYesTaskSet(groupInfoDTO.getYesTaskSet());
        entity.setNoTaskSet(groupInfoDTO.getNoTaskSet());
        entity.setCreatorId(groupInfoDTO.getCreatorId());
        entity.setCreateTime(LocalDateTime.parse(groupInfoDTO.getCreateTime()));
        return entity;
    }
}

