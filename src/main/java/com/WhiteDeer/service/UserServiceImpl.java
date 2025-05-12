package com.WhiteDeer.service;

import com.WhiteDeer.converter.UserConverter;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dao.UserRepository;
import com.WhiteDeer.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(Long id) {
        //return userRepository.getById(id).orElseThrow(Exception::new);
        User user = userRepository.getById(id);
        return UserConverter.converterUser(user);
    }

    @Override
    public UserDTO getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return UserConverter.converterUser(user);
    }

    @Override
    public Long createUser(UserDTO userDTO) throws IllegalAccessException {
        User userTemp = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if(!userTemp.equals(null)){
            throw new IllegalAccessException("电话已被占用");
        }
        User user = userRepository.save(UserConverter.converterUser(userDTO));
        return user.getId();
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id"+id+"doesn`t exist"));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateNameById(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(()->new IllegalArgumentException("id"+userDTO.getId()+"doesn`t exist"));
        user.setName(userDTO.getName());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePhoneNumberById(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(()->new IllegalArgumentException("id"+userDTO.getId()+"doesn`t exist"));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateFaceById(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(()->new IllegalArgumentException("id"+userDTO.getId()+"doesn`t exist"));
        user.setFace(userDTO.getFace());
        userRepository.save(user);
    }
}
