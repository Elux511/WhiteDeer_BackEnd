package com.WhiteDeer.service;

import com.WhiteDeer.converter.GroupInfoConverter;
import com.WhiteDeer.dao.*;
import com.WhiteDeer.dto.GroupDetailDTO;
import com.WhiteDeer.dto.GroupInfoDTO;
import com.WhiteDeer.dto.MemberDTO;
import com.WhiteDeer.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class GroupInfoServiceImpl implements GroupInfoService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Override
    public List<GroupInfoDTO> getJoinedGroups(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getJoinGroupSet() == null) {
            return Collections.emptyList();
        }
        Vector<Long> groupIds = user.getJoinGroupSet();
        List<GroupInfo> groups = groupInfoRepository.findAllById(groupIds);
        return groups.stream()
                .map(GroupInfoConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupInfoDTO> getManagedGroups(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getCreateGroupSet() == null) {
            return Collections.emptyList();
        }
        List<Long> groupIds = user.getCreateGroupSet()
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<GroupInfo> groups = groupInfoRepository.findAllById(groupIds);
        return groups.stream()
                .map(GroupInfoConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupInfoDTO> searchGroups(String idStr, String name, String beginStr, String endStr) {
        List<GroupInfo> result = new ArrayList<>();
        if (idStr != null && !idStr.isEmpty()) {
            Long groupId = Long.valueOf(idStr);
            Optional<GroupInfo> optionalGroup = groupInfoRepository.findById(groupId);
            optionalGroup.ifPresent(result::add);
        } else if (name != null && !name.isEmpty()) {
            result = groupInfoRepository.findByGroupNameContaining(name);
        } else if (beginStr != null && !beginStr.isEmpty() && endStr != null && !endStr.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime begin = LocalDateTime.parse(beginStr, formatter);
            LocalDateTime end = LocalDateTime.parse(endStr, formatter);
            result = groupInfoRepository.findByCreateTimeBetween(begin, end);
        } else {
            result = groupInfoRepository.findAll();
        }

        return result.stream()
                .map(GroupInfoConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean joinGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElse(null);
        GroupInfo group = groupInfoRepository.findById(groupId).orElse(null);
        if (user == null || group == null) {
            return false;
        }
        Vector<Long> joinGroupSet = user.getJoinGroupSet() == null ? new Vector<>() : user.getJoinGroupSet();
        if (!joinGroupSet.contains(groupId)) {
            joinGroupSet.add(groupId);
            user.setJoinGroupSet(joinGroupSet);
            userRepository.save(user);
        }
        Vector<Long> memberList = group.getMemberList() == null ? new Vector<>() : group.getMemberList();
        if(!memberList.contains(userId)) {
            memberList.add(userId);
            group.setMemberList(memberList);
            groupInfoRepository.save(group);
        }
        return true;
    }

    @Override
    public Boolean createGroup(String groupName, Long maxMember, String introduction, Long creatorId) {

        GroupInfo group = new GroupInfo();
        group.setGroupName(groupName);
        group.setGroupIntroduction(introduction);
        group.setCreatorId(creatorId);
        group.setMemberList(new Vector<>(Arrays.asList(creatorId)));
        group.setYesTaskSet(new Vector<>());
        group.setNoTaskSet(new Vector<>());
        group.setCreateTime(LocalDateTime.now());
        group.setMaxMember(maxMember);
        groupInfoRepository.save(group);

        User creator = userRepository.findById(creatorId).orElse(null);
        if (creator == null) return false;
        Vector<Long> createGroupSet = creator.getCreateGroupSet() == null ? new Vector<>() : creator.getCreateGroupSet();
        Vector<Long> joinGroupSet = creator.getJoinGroupSet() == null ? new Vector<>() : creator.getJoinGroupSet();
        Long gid = group.getGroupId();
        if (!createGroupSet.contains(gid)) createGroupSet.add(gid);
        if (!joinGroupSet.contains(gid)) joinGroupSet.add(gid);
        creator.setCreateGroupSet(createGroupSet);
        creator.setJoinGroupSet(joinGroupSet);
        userRepository.save(creator);

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteGroup(Long userId, Long groupId) {
        GroupInfo group = groupInfoRepository.findById(groupId).orElse(null);
        if (group == null) {
            return false;
        }
        if (!userId.equals(group.getCreatorId())) {
            return false;
        }
        // 获取所有用户
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            Vector<Long> joinGroups = user.getJoinGroupSet();
            if (joinGroups != null && joinGroups.contains(groupId)) {
                joinGroups.remove(groupId);
                userRepository.save(user);
            }
        }
        // 删除创建这个团队的列表
        groupInfoRepository.deleteById(groupId);
        return true;
    }

    @Override
    public GroupDetailDTO getGroupDetails(Long groupId) {
        GroupInfo group = groupInfoRepository.findById(groupId).orElse(null);
        if (group == null) {
            return null;
        }

        GroupDetailDTO groupDetail = new GroupDetailDTO();
        List<MemberDTO> members = new ArrayList<>();
        List<TaskDTO> tasks = new ArrayList<>();

        //  处理成员列表
        Vector<Long> memberIds = group.getMemberList();
        if (memberIds != null && !memberIds.isEmpty()) {
            for (Long memberId : memberIds) {
                User user = userRepository.findById(memberId).orElse(null);
                if (user != null) {
                    MemberDTO member = new MemberDTO();
                    member.setId(user.getId());
                    member.setName(user.getName());
                    member.setPhoneNumber(user.getPhoneNumber());
                    members.add(member);
                }
            }
        }
        //  处理任务列表
        List<Task> taskList = taskRepository.findByGroupId(group.getGroupId()); //    外键task
        if (taskList != null && !taskList.isEmpty()) {
            for (Task task : taskList) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setGroupName(task.getGroupName());
                taskDTO.setEndTime(task.getEndTime());
                taskDTO.setShouldCount(task.getShouldCount());
                taskDTO.setActualCount(task.getActualCount());
                tasks.add(taskDTO);
            }
        }
        groupDetail.setMemberlist(members);
        groupDetail.setTasklist(tasks);

        return groupDetail;
    }

    @Override
    public Boolean quitGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElse(null);
        GroupInfo group = groupInfoRepository.findById(groupId).orElse(null);
        if (user == null || group == null) {
            return false;
        }
        Vector<Long> joinGroupSet = user.getJoinGroupSet() == null ? new Vector<>() : user.getJoinGroupSet();
        System.out.println(hasContain(joinGroupSet, groupId));
        if (hasContain(joinGroupSet, groupId)) {
            joinGroupSet.remove(groupId);
            user.setJoinGroupSet(joinGroupSet);
            userRepository.save(user);
        }
        Vector<Long> memberList = group.getMemberList() == null ? new Vector<>() : group.getMemberList();
        if(hasContain(memberList, userId)) {
            memberList.remove(userId);
            group.setMemberList(memberList);
            groupInfoRepository.save(group);
        }
        return true;
    }

    @Override
    public void deleteTaskById(long groupId, long taskId) {
        GroupInfo group = groupInfoRepository.findById(groupId).orElse(null);
        if(group == null) {
            return;
        }
        if(hasContain(group.getYesTaskSet(), taskId)) {
            group.deteleYesTaskSet(taskId);
        }
        if(hasContain(group.getNoTaskSet(), taskId)) {
            group.deteleNoTaskSet(taskId);
        }
    }

    private boolean hasContain(Vector<Long> nums, Long num) {
        for (Long l : nums) {
            if(l.longValue() == num.longValue()){
                return true;
            }
        }
        return false;
    }

}
