package com.WhiteDeer.Controller;


import com.WhiteDeer.Response;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.WhiteDeer.converter.BlobConverter.blobToBase64;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //手机号登录
    @PostMapping("/api/login1")
    public Response<Map<String, Object>> loginByPhoneNumber(@RequestBody UserDTO userDTO) {
        return userService.getUserByPhoneNumber(userDTO.getPhoneNumber())
                .map(user -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", user.getId());
                    data.put("haveface", user.isHaveface());
                    return Response.newSuccess(1,data);
                })
                .orElseGet(() -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", null);
                    data.put("haveface", null);
                    return Response.newFailed(2,data);
                });
    }

    //账号密码登录
    @PostMapping("/api/login2")
    public Response<Map<String, Object>> loginByID(@RequestBody UserDTO userDTO) {
        return userService.getUserById(userDTO.getId())
                .map(user -> {
                    if (!user.getPassword().equals(userDTO.getPassword())) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("haveface", null);
                        return Response.newFailed(3,data);
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("haveface", user.isHaveface());
                    return Response.newSuccess(1,data);
                })
                .orElseGet(() -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("haveface", null);
                    return Response.newFailed(2,data);
                });
    }

    //获取个人信息
    @GetMapping("/api/myinfo/")
    public Response<Map<String, Object>> getUserById(@RequestParam long id) {
        // 调用服务层获取用户信息
        Optional<UserDTO> userDTOOptional = userService.getUserById(id);
        // 如果用户不存在，返回失败响应
        if (userDTOOptional.isEmpty()) {
            return Response.newFailed(2, null);
        }
        // 获取用户信息
        UserDTO userDTO = userDTOOptional.get();
        // 处理人脸图片（Blob转Base64）
        if (userDTO.getFace() != null) {
            try {
                Blob blob = userDTO.getFace();
                String base64Image = blobToBase64(blob);
                String face="data:image/jpeg;base64," + base64Image;
            } catch (IOException e) {
                String face= null;
                Map<String, Object> data = new HashMap<>();
                data.put("name", userDTO.getName());
                data.put("id", userDTO.getId());
                data.put("phoneNumber", userDTO.getPhoneNumber());
                data.put("face", face);
                return Response.newSuccess(2, data);
            }
        }
        // 构建响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("name", userDTO.getName());
        data.put("id", userDTO.getId());
        data.put("phoneNumber", userDTO.getPhoneNumber());
        data.put("face", userDTO.getFace());
        // 返回成功响应
        return Response.newSuccess(1, data);
    }

    //注册新用户
    @PostMapping("/api/register")
    public Response<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        try {
            Long userId = userService.createUser(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userId);
            return Response.newSuccess(1, data);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", null);
            return Response.newFailed(2, data);
        }
    }

    //删除用户
    @DeleteMapping("/api/deleteuser")
    public Response<Map<String, Object>> deleteUserById(@RequestParam long id) {
        try {
            userService.deleteUserById(id);
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("success", true);
            return Response.newSuccess(1, data);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("success", false);
            return Response.newFailed(2, data);
        }
    }

    //修改用户name
    @PatchMapping("/api/changename")
    public Response<Void> updateNameById(@RequestBody UserDTO userDTO) {
        try {
            userService.updateNameById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newState(1);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            return Response.newState(2);
        }
    }

    //修改用户phoneNumber
    @PatchMapping("/api/changephone")
    public Response<Void> updatePhoneNumberById(@RequestBody UserDTO userDTO) {
        try {
            userService.updatePhoneNumberById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newState(1);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            return Response.newState(2);
        }
    }

    @PatchMapping("/api/changepassword")
    public Response<Void> updatePasswordById(@RequestBody UserDTO userDTO) {
        try{
            userService.updatePasswordById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newState(1);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            return Response.newState(2);
        }
    }

//    //修改用户face
//    @PatchMapping("/api/setface")
//    public void updateFaceById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
//        userService.updateFaceById(userDTO);
//    }

}
