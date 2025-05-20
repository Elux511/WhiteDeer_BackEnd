package com.WhiteDeer.service;

import com.WhiteDeer.dto.UserDTO;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUserById(Long id);

    Optional<UserDTO> getUserByPhoneNumber(String phoneNumber);

    Long createUser(UserDTO userDTO) throws IllegalArgumentException;

    void deleteUserById(long id);

    void updateNameById(UserDTO userDTO);

    void updatePhoneNumberById(UserDTO userDTO);

    void updatePasswordById(UserDTO userDTO);

    void updateFaceById(UserDTO userDTO);

    void acceptTaskById(long userId, long taskId);

    void finishTaskById(long userId, long taskId);

    void deleteTaskById(long userId, long taskId);

}
