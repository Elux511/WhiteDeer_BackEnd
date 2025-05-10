package com.WhiteDeer.service;

import com.WhiteDeer.entity.User;
import com.WhiteDeer.exception.UserNotFoundException;
import com.WhiteDeer.mapper.dto.UserDto;
import com.WhiteDeer.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User add(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

    public User getUser(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    @Transactional
    public User update(UserDto userDto) {
        User existingUser = getUser(userDto.getId());
        BeanUtils.copyProperties(userDto, existingUser);
        return userRepository.save(existingUser);
    }

    @Transactional
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void addUserToGroup(String userId, String groupId) {
        userRepository.addGroup(userId, groupId);
    }

    @Transactional
    public void removeUserFromGroup(String userId, String groupId) {
        userRepository.removeGroup(userId, groupId);
    }

    @Transactional
    public void markTaskAsCompleted(String userId, String taskId) {
        userRepository.addYesTask(userId, taskId);
    }

    @Transactional
    public void markTaskAsNotCompleted(String userId, String taskId) {
        userRepository.addNoTask(userId, taskId);
    }

    public Set<String> getUserGroups(String userId) {
        return getUser(userId).getGroupSet();
    }

    public Set<String> getCompletedTasks(String userId) {
        return getUser(userId).getYesTaskSet();
    }

    public Set<String> getNotCompletedTasks(String userId) {
        return getUser(userId).getNoTaskSet();
    }
}