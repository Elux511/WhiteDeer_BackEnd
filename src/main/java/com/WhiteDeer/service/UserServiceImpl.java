package com.WhiteDeer.service;

import com.WhiteDeer.converter.UserConverter;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dao.UserRepository;
import com.WhiteDeer.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    //通过ID获取用户
    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserConverter::converterUser);
    }

    //通过手机号获取用户 注意：只会获取第一个
    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .stream()
                .findFirst()
                .map(UserConverter::converterUser);
    }

    //创建用户
    @Override
    public Long createUser(UserDTO userDTO) throws IllegalArgumentException {
        // 检查手机号是否已存在
        Optional<User> existingUser = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("手机号已被注册: " + userDTO.getPhoneNumber());
        }

        // 转换DTO并保存用户
        User user = UserConverter.converterUser(userDTO);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    //根据ID删除用户
    public void deleteUserById(long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        user -> userRepository.delete(user),
                        () -> { throw new IllegalArgumentException("用户ID不存在: " + id); }
                );
    }

    //根据ID更新用户名
    @Override
    @Transactional
    public void updateNameById(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            user.setName(userDTO.getName());
                            userRepository.save(user);
                        },
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + userDTO.getId());
                        }
                );
    }

    //根据ID更新密码
    @Override
    @Transactional
    public void updatePasswordById(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            user.setPassword(userDTO.getPassword());
                            userRepository.save(user);
                        },
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + userDTO.getId());
                        }
                );
    }

    //根据ID更新手机号
    @Override
    @Transactional
    public void updatePhoneNumberById(UserDTO userDTO) {
        // 检查新手机号是否已被其他用户使用
        getUserByPhoneNumber(userDTO.getPhoneNumber())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userDTO.getId())) {
                        throw new IllegalArgumentException("手机号已被其他用户占用: " + userDTO.getPhoneNumber());
                    }
                });

        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            user.setPhoneNumber(userDTO.getPhoneNumber());
                            userRepository.save(user);
                        },
                        () -> {
                            throw new EntityNotFoundException("用户ID不存在: " + userDTO.getId());
                        }
                );
    }

    //根据ID更新人脸
    public void updateFaceById(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            user.setFace(userDTO.getFace());
                            userRepository.save(user);
                        },
                        () -> {
                            throw new EntityNotFoundException("用户ID不存在: " + userDTO.getId());
                        }
                );
    }

    //根据ID获得发布的打卡任务
    @Override
    @Transactional
    public void acceptTaskById(long userId, long taskId) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        user -> {
                            user.addNo(taskId);
                            userRepository.save(user);
                        },
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + userId);
                        }
                );
    }

    //根据ID完成打卡任务
    @Override
    @Transactional
    public void finishTaskById(long userId, long taskId) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        user -> {
                            user.addYes(taskId);
                            user.deleteNo(taskId);
                            userRepository.save(user);
                        },
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + userId);
                        }
                );
    }
}
