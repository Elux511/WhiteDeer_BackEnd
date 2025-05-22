package com.WhiteDeer.service;

import com.WhiteDeer.converter.BlobConverter;
import com.WhiteDeer.converter.UserConverter;
import com.WhiteDeer.dao.*;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.util.FaceException;
import com.WhiteDeer.util.PyAPI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.DOMException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupInfoRepository groupRepository;
    @Autowired
    private GroupInfoRepository groupInfoRepository;

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
    @Transactional
    public Long createUser(UserDTO userDTO) throws IllegalArgumentException {
        Optional<User> existingUser = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("手机号已被注册: " + userDTO.getPhoneNumber());
        }
        User user = UserConverter.converterUser(userDTO);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    //根据ID删除用户
    @Override
    @Transactional
    public void deleteUserById(long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        user -> {
                            taskRepository.removeUserFromAllTasks(id);
                            groupInfoRepository.removeUserFromAllGroups(id);
                            userRepository.delete(user);
                            },
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + id);
                        }
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
    @Override
    @Transactional
    public void updateFaceById(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            String img = null;
                            try {img = BlobConverter.blobToBase64(userDTO.getFace());} catch (IOException e) {throw new DOMException((short) 12,"Blob无法转换为base64编码");}//尝试将Blob转为base64
                            CompletableFuture<Boolean> success = PyAPI.trainFaceLabelsAsync(String.valueOf(userDTO.getId()), img);
                            success.thenAccept(result -> {
                                if(result){
                                    user.setFace(userDTO.getFace());
                                    userRepository.save(user);
                                }
                            });
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
                            if(user.getNoTaskSet() == null){user.setNoTaskSet(new Vector());}
                            if(!user.getNoTaskSet().contains(taskId)) {
                                user.addNo(taskId);
                                userRepository.save(user);
                            }
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

    //删除user的对应打卡任务
    @Override
    @Transactional
    public void deleteTaskById(long userId, long taskId) {
        userRepository.findById(userId).ifPresentOrElse(
                user -> {
                    if(user.getYesTaskSet() == null) {user.setYesTaskSet(new Vector<>());}
                    if(user.getNoTaskSet() == null) {user.setNoTaskSet(new Vector<>());}
                    if (user.getYesTaskSet().contains(taskId)) {user.deleteYes(taskId);}
                    if (user.getNoTaskSet().contains(taskId)) {user.deleteNo(taskId);}
                    userRepository.save(user);
                },
                ()->{
                    throw new IllegalArgumentException("用户ID不存在: " + userId);
                }
        );
    }
}
