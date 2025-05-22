package com.WhiteDeer.cache;


import com.WhiteDeer.dao.GroupInfo;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class GroupInfoCacheImpl implements GroupInfoCache{
    private static final String GROUP_KEY_PREFIX = "group:";
    private static final String GROUP_TASKS_KEY_PREFIX = "group_tasks:";
    private static final long CACHE_EXPIRE_TIME = 3600;//1hour 缓存时间

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Optional<GroupInfo> getGroupInfo(String groupId){
        String key = GROUP_KEY_PREFIX + groupId;
        String groupJson = redisTemplate.opsForValue().get(key);
        if (groupJson != null) {
            try {
                return Optional.of(objectMapper.readValue(groupJson, GroupInfo.class));
            } catch (Exception e) {
                // 记录异常日志
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    @Override
    public  void saveGroupInfo(GroupInfo groupInfo){
        try {
            // 缓存单个组
            String groupKey = GROUP_KEY_PREFIX + groupInfo.getGroupId();
            String groupJson = objectMapper.writeValueAsString(groupInfo);
            redisTemplate.opsForValue().set(groupKey, groupJson, CACHE_EXPIRE_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
