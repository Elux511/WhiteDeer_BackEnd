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
        return userDTO;
    }

    public static User converterUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setFace(userDTO.getFace());
        return user;
    }
}
