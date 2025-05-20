package com.WhiteDeer.cache;

import com.WhiteDeer.dao.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TaskCacheImpl implements TaskCache {

    private static final String TASK_KEY_PREFIX = "task:";
    private static final String GROUP_TASKS_KEY_PREFIX = "group_tasks:";
    private static final long CACHE_EXPIRE_TIME = 3600; // 1小时

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        String key = TASK_KEY_PREFIX + taskId;
        String taskJson = redisTemplate.opsForValue().get(key);
        if (taskJson != null) {
            try {
                return Optional.of(objectMapper.readValue(taskJson, Task.class));
            } catch (Exception e) {
                // 记录异常日志
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public void saveTask(Task task) {
        try {
            // 缓存单个任务
            String taskKey = TASK_KEY_PREFIX + task.getId();
            String taskJson = objectMapper.writeValueAsString(task);
            redisTemplate.opsForValue().set(taskKey, taskJson, CACHE_EXPIRE_TIME, TimeUnit.SECONDS);

            // 更新分组任务列表
            String groupKey = GROUP_TASKS_KEY_PREFIX + task.getGroupId();
            redisTemplate.opsForList().remove(groupKey, 0, taskKey); // 先移除旧数据
            redisTemplate.opsForList().rightPush(groupKey, taskJson);
            redisTemplate.expire(groupKey, CACHE_EXPIRE_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        // 删除单个任务缓存
        String taskKey = TASK_KEY_PREFIX + taskId;
        Task task = getTaskById(taskId).orElse(null);
        redisTemplate.delete(taskKey);

        // 从分组列表中移除
        if (task != null) {
            String groupKey = GROUP_TASKS_KEY_PREFIX + task.getGroupId();
            redisTemplate.opsForList().remove(groupKey, 0, taskKey);
        }
    }

    @Override
    public void batchSaveTasks(List<Task> tasks) {
        // 使用Redis Pipeline批量操作
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            tasks.forEach(task -> {
                try {
                    String taskKey = TASK_KEY_PREFIX + task.getTaskId();
                    String taskJson = objectMapper.writeValueAsString(task);
                    connection.setEx(
                            taskKey.getBytes(),
                            CACHE_EXPIRE_TIME,
                            taskJson.getBytes()
                    );

                    String groupKey = GROUP_TASKS_KEY_PREFIX + task.getGroupId();
                    connection.rPush(
                            groupKey.getBytes(),
                            taskJson.getBytes()
                    );
                    connection.expire(groupKey.getBytes(), CACHE_EXPIRE_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return null;
        });
    }

    @Override
    public void refreshTaskCache() {
        // 定时全量刷新缓存逻辑
        // 实际项目中应通过消息队列或定时任务触发
    }
}