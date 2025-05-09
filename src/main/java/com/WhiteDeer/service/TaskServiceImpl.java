package com.WhiteDeer.service;
import com.WhiteDeer.entity.Task;
import com.WhiteDeer.mapper.dto.TaskDto;


public interface TaskServiceImpl {
    /**
     * 添加打卡任务
     * @param task
     * @return
     */
    Task add(TaskDto task);

    /**
     * 查询打卡任务
     * @param task 打卡任务ID
     * @return
     */
    Task getTask(String taskId);

    /**
     * 修改打卡任务
     * @param task
     * @return
     */
    Task edit(TaskDto task);

    /**
     * 删除打卡任务
     * @param task
     * @return
     */
    Task delete(TaskDto task);
}
