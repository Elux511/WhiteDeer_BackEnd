package com.WhiteDeer.converter;


import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.UserDTO;

public class UserConverter {

    public static UserDTO converterUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setFace(user.getFace());
        userDTO.setJoinGroupSet(user.getJoinGroupSet());
        userDTO.setCreateGroupSet(user.getCreateGroupSet());
        userDTO.setYesTaskSet(user.getYesTaskSet());
        userDTO.setNoTaskSet(user.getNoTaskSet());
        return userDTO;
    }

    public static User converterUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setFace(userDTO.getFace());
        user.setJoinGroupSet(userDTO.getJoinGroupSet());
        user.setCreateGroupSet(userDTO.getCreateGroupSet());
        user.setYesTaskSet(userDTO.getYesTaskSet());
        user.setNoTaskSet(userDTO.getNoTaskSet());
        return user;
    }
}
