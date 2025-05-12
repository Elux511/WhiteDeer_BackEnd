package com.WhiteDeer.service;

import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.UserDTO;

public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO getUserByPhoneNumber(String phoneNumber);

    Long createUser(UserDTO userDTO) throws IllegalAccessException;

    void deleteUserById(long id);

    void updateNameById(UserDTO userDTO);

    void updatePhoneNumberById(UserDTO userDTO);

    void updateFaceById(UserDTO userDTO);
}
