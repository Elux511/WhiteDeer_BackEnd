package com.WhiteDeer.service;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.entity.GroupMember;
import com.WhiteDeer.exception.GroupNotFoundException;
import com.WhiteDeer.mapper.dto.GroupDto;
import com.WhiteDeer.repository.GroupRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        BeanUtils.copyProperties(groupDto, group);

        // 添加创建者为管理员
        GroupMember creator = new GroupMember(
                groupDto.getCreatorId(),
                "admin",
                java.time.LocalDateTime.now().toString()
        );
        group.addMember(creator);

        return groupRepository.save(group);
    }

    public Group getGroup(String groupId) {
        Group group = groupRepository.findById(groupId);
        if (group == null) {
            throw new GroupNotFoundException(groupId);
        }
        return group;
    }

    @Transactional
    public Group updateGroup(GroupDto groupDto) {
        Group existingGroup = getGroup(groupDto.getId());
        BeanUtils.copyProperties(groupDto, existingGroup);
        return groupRepository.save(existingGroup);
    }

    @Transactional
    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }

    @Transactional
    public void addMemberToGroup(String groupId, String userId, String role) {
        GroupMember member = new GroupMember(
                userId,
                role,
                java.time.LocalDateTime.now().toString()
        );
        groupRepository.addMember(groupId, member);
    }

    @Transactional
    public void removeMemberFromGroup(String groupId, String userId) {
        groupRepository.removeMember(groupId, userId);
    }

    @Transactional
    public void markTaskAsCompleted(String groupId, String taskId) {
        groupRepository.addYesTask(groupId, taskId);
    }

    @Transactional
    public void markTaskAsNotCompleted(String groupId, String taskId) {
        groupRepository.addNoTask(groupId, taskId);
    }

    public Set<GroupMember> getGroupMembers(String groupId) {
        return getGroup(groupId).getMemberList();
    }

    public Set<String> getCompletedTasks(String groupId) {
        return getGroup(groupId).getYesTaskSet();
    }

    public Set<String> getNotCompletedTasks(String groupId) {
        return getGroup(groupId).getNoTaskSet();
    }
}