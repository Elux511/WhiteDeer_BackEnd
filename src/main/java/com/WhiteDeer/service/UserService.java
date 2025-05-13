package com.WhiteDeer.service;

import com.WhiteDeer.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUserById(Long id);

    Optional<UserDTO> getUserByPhoneNumber(String phoneNumber);

    Long createUser(UserDTO userDTO) throws IllegalAccessException;

    void deleteUserById(long id);

    void updateNameById(UserDTO userDTO);

    void updatePhoneNumberById(UserDTO userDTO);

    void updateFaceById(UserDTO userDTO);

    void finishTaskById(long userId, long taskId);
}
