package com.WhiteDeer.Controller;


import com.WhiteDeer.Response;
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
//    @GetMapping("/api/myinfo/")
//    public Response<UserDTO> getUserById(@RequestParam long id) {
//        return userService.getUserById(id)
//                .map(Response::<UserDTO>myinfoSuccess)
//                .orElseGet(() -> Response.myinfoFailed("用户不存在"));
//    }

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

    //修改用户name--要加异常处理
    @PatchMapping("/api/changename")
    public Response<Void> updateNameById(@RequestBody UserDTO userDTO) {
        userService.updateNameById(userDTO);
        return Response.newState(1);
    }

    //修改用户phoneNumber--要加异常处理
    @PatchMapping("/api/changephone")
    public Response<Void> updatePhoneNumberById(@RequestBody UserDTO userDTO) {
        userService.updatePhoneNumberById(userDTO);
        return Response.newState(1);
    }

//    //修改用户face
//    @PatchMapping("/api/setface")
//    public void updateFaceById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
//        userService.updateFaceById(userDTO);
//    }

}
