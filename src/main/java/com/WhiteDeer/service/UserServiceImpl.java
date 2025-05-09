package com.WhiteDeer.service.impl;

import com.WhiteDeer.entity.User;
import com.WhiteDeer.exception.UserNotFoundException;
import com.WhiteDeer.mapper.dto.UserDto;
import com.WhiteDeer.repository.UserRepository;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User add(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    @Override
    @Transactional
    public User update(UserDto userDto) {
        User existingUser = getUser(userDto.getId());
        BeanUtils.copyProperties(userDto, existingUser);
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void addUserToGroup(String userId, String groupId) {
        userRepository.addGroup(userId, groupId);
    }

    @Override
    @Transactional
    public void removeUserFromGroup(String userId, String groupId) {
        userRepository.removeGroup(userId, groupId);
    }

    @Override
    @Transactional
    public void markTaskAsCompleted(String userId, String taskId) {
        userRepository.addYesTask(userId, taskId);
    }

    @Override
    @Transactional
    public void markTaskAsNotCompleted(String userId, String taskId) {
        userRepository.addNoTask(userId, taskId);
    }

    @Override
    public Set<String> getUserGroups(String userId) {
        return getUser(userId).getGroupSet();
    }

    @Override
    public Set<String> getCompletedTasks(String userId) {
        return getUser(userId).getYesTaskSet();
    }

    @Override
    public Set<String> getNotCompletedTasks(String userId) {
        return getUser(userId).getNoTaskSet();
    }
}