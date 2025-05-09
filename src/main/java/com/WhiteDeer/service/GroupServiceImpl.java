package com.WhiteDeer.service;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.mapper.dto.GroupDto;


public interface GroupServiceImpl {
    /**
     * 添加组
     * @param task
     * @return
     */
    Group add(GroupDto group);

    /**
     * 查询组
     * @param task 打卡任务ID
     * @return
     */
    Group getTask(GroupDto group);

    /**
     * 修改组
     * @param task
     * @return
     */
    Group edit(GroupDto group);

    /**
     * 删除打卡任务
     * @param task
     * @return
     */
    Group delete(GroupDto group);
}

