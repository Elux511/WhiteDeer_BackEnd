package com.WhiteDeer.service;
import com.WhiteDeer.User;
import com.WhiteDeer.mapper.dto.UserDto;


public interface UserServiceImpl {
    /**
     * 添加成员
     * @param
     * @return
     */
    User add(UserDto user);

    /**
     * 查询成员
     * @param
     * @return
     */
    User getTask(UserDto user);

    /**
     * 修改成员
     * @param
     * @return
     */
    User edit(UserDto user);

    /**
     * 删除成员
     * @param
     * @return
     */
    User delete(UserDto user);
}
