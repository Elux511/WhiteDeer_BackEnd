package com.WhiteDeer.service;

import com.WhiteDeer.converter.BlobConverter;
import com.WhiteDeer.converter.UserConverter;
import com.WhiteDeer.dao.*;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.util.FaceException;
import com.WhiteDeer.util.PyAPI;
import com.WhiteDeer.util.RedisSerializationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.DOMException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${user.expire-minutes}")
    private int EXPIRE_MINUTES;

    private static final String USER_CACHE_KEY = "user:";
    private static final String PHONE_CACHE_KEY = "phone:";

    //通过ID获取用户
    @Override
    public Optional<UserDTO> getUserById(Long id) {
        String key = buildUserCacheKey(id);

        String cachedJson = stringRedisTemplate.opsForValue().get(key);
        if (cachedJson != null) {
            try {
                UserDTO userDTO = RedisSerializationUtils.deserializeObject(cachedJson, UserDTO.class);
                return Optional.of(userDTO);
            } catch (IOException e) {
                //log.error("反序列化 UserDTO 失败: {}", e.getMessage());
                stringRedisTemplate.delete(key);
            }
        }

        String lockKey = "lock:user:" + id;
        Boolean acquired = stringRedisTemplate.opsForValue()
                .setIfAbsent(lockKey, "locked", 3, TimeUnit.SECONDS);

        if (acquired != null && acquired) {
            try {
                cachedJson = stringRedisTemplate.opsForValue().get(key);
                if (cachedJson != null) {
                    return Optional.of(RedisSerializationUtils.deserializeObject(cachedJson, UserDTO.class));
                }

                return userRepository.findById(id)
                        .map(user -> {
                            UserDTO dto = UserConverter.converterUser(user);
                            try {
                                String json = RedisSerializationUtils.serializeObject(dto);
                                long expireTime = EXPIRE_MINUTES * 60 + ThreadLocalRandom.current().nextInt(60);
                                stringRedisTemplate.opsForValue().set(key, json, expireTime, TimeUnit.SECONDS);
                            } catch (JsonProcessingException e) {
                                //log.error("序列化 UserDTO 失败: {}", e.getMessage());
                            }
                            return dto;
                        });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } finally {
                stringRedisTemplate.delete(lockKey);
            }
        } else {
            // 降级处理：直接返回空值
            return Optional.empty();
        }
    }

    //通过手机号获取用户 注意：只会获取第一个
    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        String key = buildPhoneCacheKey(phoneNumber);
        String cachedJson = stringRedisTemplate.opsForValue().get(key);

        if (cachedJson != null) {
            try {
                // 反序列化（自动处理 Blob 字段）
                UserDTO cachedUser = RedisSerializationUtils.deserializeObject(cachedJson, UserDTO.class);
                return Optional.of(cachedUser);
            } catch (JsonProcessingException e) {
                // log.error("反序列化 UserDTO 失败，phoneNumber={}: {}", phoneNumber, e.getMessage());
                // 缓存数据损坏，删除缓存
                stringRedisTemplate.delete(key);
            }
        }

        // 查询数据库
        return userRepository.findByPhoneNumber(phoneNumber)
                .stream()
                .findFirst()
                .map(user -> {
                    UserDTO dto = UserConverter.converterUser(user);
                    try {
                        // 序列化对象为 JSON 字符串（自动处理 Blob 字段）
                        String json = RedisSerializationUtils.serializeObject(dto);
                        stringRedisTemplate.opsForValue().set(key, json, EXPIRE_MINUTES, TimeUnit.MINUTES);
                    } catch (JsonProcessingException e) {
                        //log.error("序列化 UserDTO 失败，phoneNumber={}: {}", phoneNumber, e.getMessage());
                    }
                    return dto;
                });
    }

    //创建用户
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserDTO userDTO){
        Optional<User> existingUser = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("手机号已被注册: " + userDTO.getPhoneNumber());
        }
        User user = UserConverter.converterUser(userDTO);
        User savedUser = userRepository.save(user);
        String phoneKey = buildPhoneCacheKey(userDTO.getPhoneNumber());
        clearCache(phoneKey);
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
    @Transactional(rollbackFor = Exception.class)
    public void updateNameById(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("用户ID不存在: " + userDTO.getId()));
        if (!Objects.equals(user.getName(), userDTO.getName())) {
            user.setName(userDTO.getName());
            userRepository.save(user);
            String idKey = buildUserCacheKey(user.getId());
            clearCache(idKey);
        }
    }

    //根据ID更新密码
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePasswordById(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            user.setPassword(userDTO.getPassword());
                            userRepository.save(user);
                            String idKey = buildUserCacheKey(user.getId());
                            clearCache(idKey);
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
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("用户ID不存在: " + userDTO.getId()));
        if (!Objects.equals(user.getPhoneNumber(), userDTO.getPhoneNumber())) {
            userRepository.findByPhoneNumber(userDTO.getPhoneNumber())
                    .ifPresent(existingUser -> {
                        if (existingUser.getId()==user.getId()) {
                            throw new IllegalArgumentException("手机号已被其他用户占用: " + userDTO.getId());
                        }
                    });
            String oldPhoneKey = buildPhoneCacheKey(user.getPhoneNumber());
            clearCache(oldPhoneKey);
            user.setPhoneNumber(userDTO.getPhoneNumber());
            userRepository.save(user);
            String idKey = buildUserCacheKey(user.getId());
            clearCache(idKey);
        }
    }

    //根据ID更新人脸
    @Override
    @Transactional
    public void updateFaceById(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(
                        user -> {
                            //检测是否存在人脸
                            String img = null;
                            try {img = BlobConverter.blobToBase64(userDTO.getFace());} catch (IOException e) {throw new DOMException((short) 12,"Blob无法转换为base64编码");}//尝试将Blob转为base64
                            CompletableFuture<Boolean> success = PyAPI.trainFaceLabelsAsync(String.valueOf(userDTO.getId()), img);
                            success.thenAccept(result -> {
                                if(result){
                                    user.setFace(userDTO.getFace());
                                    userRepository.save(user);
                                    String idKey = buildUserCacheKey(user.getId());
                                    clearCache(idKey);
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

    // 构建用户缓存键
    private String buildUserCacheKey(Long userId) {
        return USER_CACHE_KEY + userId;
    }

    // 构建手机号缓存键
    private String buildPhoneCacheKey(String phoneNumber) {
        return USER_CACHE_KEY + PHONE_CACHE_KEY + phoneNumber;
    }

    // 清除缓存
    private void clearCache(String key) {
        try {
            for (int i = 0; i < 3; i++) {
                Boolean deleted = redisTemplate.delete(key);
                if (deleted != null && deleted) {
                    return;
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
