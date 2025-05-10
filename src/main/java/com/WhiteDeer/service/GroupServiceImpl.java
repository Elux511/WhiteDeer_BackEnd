package com.WhiteDeer.service.impl;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.entity.GroupMember;
import com.WhiteDeer.exception.GroupNotFoundException;
import com.WhiteDeer.mapper.dto.GroupDto;
import com.WhiteDeer.repository.GroupRepository;
import com.WhiteDeer.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 群组服务实现类
 */
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
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

    @Override
    public Group getGroup(String groupId) throws GroupNotFoundException {
        Group group = groupRepository.findById(groupId);
        if (group == null) {
            throw new GroupNotFoundException(groupId);
        }
        return group;
    }

    @Override
    @Transactional
    public Group updateGroup(GroupDto groupDto) {
        Group existingGroup = getGroup(groupDto.getId());
        BeanUtils.copyProperties(groupDto, existingGroup);
        return groupRepository.save(existingGroup);
    }

    @Override
    @Transactional
    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    @Transactional
    public void addMemberToGroup(String groupId, String userId, String role) {
        GroupMember member = new GroupMember(
                userId,
                role,
                java.time.LocalDateTime.now().toString()
        );
        groupRepository.addMember(groupId, member);
    }

    @Override
    @Transactional
    public void removeMemberFromGroup(String groupId, String userId) {
        groupRepository.removeMember(groupId, userId);
    }

    @Override
    @Transactional
    public void markTaskAsCompleted(String groupId, String taskId) {
        groupRepository.addYesTask(groupId, taskId);
    }

    @Override
    @Transactional
    public void markTaskAsNotCompleted(String groupId, String taskId) {
        groupRepository.addNoTask(groupId, taskId);
    }

    @Override
    public Set<GroupMember> getGroupMembers(String groupId) {
        return getGroup(groupId).getMemberList();
    }

    @Override
    public Set<String> getCompletedTasks(String groupId) {
        return getGroup(groupId).getYesTaskSet();
    }

    @Override
    public Set<String> getNotCompletedTasks(String groupId) {
        return getGroup(groupId).getNoTaskSet();
    }
}