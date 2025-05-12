package com.WhiteDeer.Controller;


import com.WhiteDeer.Response;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //手机号登录
    @PostMapping("/api/login1")
    public Response<Map<String, Object>> loginByPhoneNumber(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.getUserByPhoneNumber(userDTO.getPhoneNumber());
        if (user.equals(null)) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", null);
            data.put("haveface", null);
            return Response.newSuccess(data);
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("id", user.getId());
            data.put("haveface", user.isHaveface());
            return Response.newSuccess(data);
        }
    }

    //账号密码登录
    @PostMapping("/api/login2")
    public Response<Void> loginByPhoneNumber2(@RequestBody UserDTO userDTO){
        UserDTO user = userService.getUserById(userDTO.getId());
        if(user.equals(null)) {
            return Response.newstate(2);
        }else if(!user.getPassword().equals(userDTO.getPassword())) {
            return Response.newstate(3);
        }
        else
            return Response.newstate(1);
    }

    //获取个人信息
    @GetMapping("/api/myinfo/")
    public Response<UserDTO> getUserById(@RequestParam long id) {
        return Response.newSuccess(userService.getUserById(id));
    }

    //注册新用户
    @PostMapping("/api/register")
    public Response<Long> createUser(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        return Response.newSuccess(userService.createUser(userDTO));
    }

    //删除用户
    @DeleteMapping("/api/deleteuser")
    public void deleteUserById(@RequestParam long id) {
        userService.deleteUserById(id);
    }

    //修改用户name
    @PatchMapping("/api/changename")
    public void updateNameById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        userService.updateNameById(userDTO);
    }

    //修改用户phoneNumber
    @PatchMapping("/api/changephone")
    public void updatePhoneNumberById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        userService.updatePhoneNumberById(userDTO);
    }

    //修改用户face
    @PatchMapping("/api/setface")
    public void updateFaceById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        userService.updateFaceById(userDTO);
    }
}
