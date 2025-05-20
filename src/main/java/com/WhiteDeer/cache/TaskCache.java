package com.WhiteDeer.cache;

import com.WhiteDeer.dao.Task;
import java.util.List;
import java.util.Optional;

public interface TaskCache {
    // 读取缓存
    Optional<Task> getTaskById(Long taskId);

    // 更新缓存
    void saveTask(Task task);
    void deleteTask(Long taskId);

    // 批量操作
    void batchSaveTasks(List<Task> tasks);
    void refreshTaskCache();
}