package com.WhiteDeer.Controller;


import com.WhiteDeer.Response;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
                    data.put("message", "用户不存在");
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
                        data.put("message", "密码错误");
                        return Response.newFailed(3,data);
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("haveface", user.isHaveface());
                    return Response.newSuccess(1,data);
                })
                .orElseGet(() -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("haveface", null);
                    data.put("message", "用户不存在");
                    return Response.newFailed(2,data);
                });
    }

    //获取个人信息
    @GetMapping("/api/myinfo")
    public Response<Map<String, Object>> getUserById(@RequestParam long id) {
        Optional<UserDTO> userDTOOptional = userService.getUserById(id);
        if (userDTOOptional.isEmpty()) {
            Map<String, Object> data=new HashMap<>();
            data.put("message", "用户不存在");
            return Response.newFailed(2, data);
        }
        UserDTO userDTO = userDTOOptional.get();
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
                data.put("message", e.getMessage());
                return Response.newFailed(2, data);
            }catch (Exception e) {
                String face= null;
                Map<String, Object> data = new HashMap<>();
                data.put("name", userDTO.getName());
                data.put("id", userDTO.getId());
                data.put("phoneNumber", userDTO.getPhoneNumber());
                data.put("face", face);
                data.put("message", "未知错误获取个人信息失败");
                data.put("message", e.getMessage());
                return Response.newFailed(3, data);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("name", userDTO.getName());
        data.put("id", userDTO.getId());
        data.put("phoneNumber", userDTO.getPhoneNumber());
        data.put("face", userDTO.getFace());
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
            data.put("message", e.getMessage());
            return Response.newFailed(2, data);
        }catch (Exception e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", "未知错误注册新用户失败");
            data.put("message", e.getMessage());
            return Response.newFailed(3, data);
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
            data.put("message", e.getMessage());
            return Response.newFailed(2, data);
        }catch (Exception e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("success", false);
            data.put("message", "未知错误删除用户失败");
            data.put("message", e.getMessage());
            return Response.newFailed(3, data);
        }
    }

    //修改用户name
    @PatchMapping("/api/changename")
    public Response<Map<String, Object>> updateNameById(@RequestBody UserDTO userDTO) {
        try {
            userService.updateNameById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newSuccess(1,data);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", e.getMessage());
            return Response.newSuccess(2,data);
        }catch (Exception e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", "未知错误更新名字失败");
            data.put("message", e.getMessage());
            return Response.newFailed(3, data);
        }
    }

    //修改用户phoneNumber
    @PatchMapping("/api/changephone")
    public Response<Map<String, Object>> updatePhoneNumberById(@RequestBody UserDTO userDTO) {
        try {
            userService.updatePhoneNumberById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newSuccess(1,data);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", e.getMessage());
            return Response.newSuccess(2,data);
        }catch (Exception e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", "未知错误更新手机号失败");
            data.put("message", e.getMessage());
            return Response.newFailed(3, data);
        }
    }

    //修改用户password
    @PatchMapping("/api/changepassword")
    public Response<Map<String, Object>> updatePasswordById(@RequestBody UserDTO userDTO) {
        try{
            userService.updatePasswordById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newSuccess(1,data);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", e.getMessage());
            return Response.newSuccess(2,data);
        }catch (Exception e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", "未知错误更新密码失败");
            data.put("message", e.getMessage());
            return Response.newFailed(3, data);
        }
    }

    //修改用户face
    @PatchMapping("/api/setface")
    public Response<Map<String, Object>> updateFaceById(@RequestBody UserDTO userDTO) {
        try {
            userService.updateFaceById(userDTO);
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", true);
            return Response.newSuccess(1, data);
        } catch (EntityNotFoundException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", e.getMessage());
            return Response.newFailed(2, data);
        } catch (Exception e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userDTO.getId());
            data.put("success", false);
            data.put("message", "未知错误更新头像失败");
            data.put("message", e.getMessage());
            return Response.newFailed(3, data);
        }
    }

}
