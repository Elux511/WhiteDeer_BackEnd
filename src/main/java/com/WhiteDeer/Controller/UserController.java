package com.WhiteDeer.Controller;


import com.WhiteDeer.Response;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //手机号登录
    @PostMapping("/api/login1")
    public Response<Long> loginByPhoneNumber(@RequestBody UserDTO userDTO){
        return Response.newSuccess(userService.getUserByPhoneNumber(userDTO.getPhoneNumber()).getId());
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
    @GetMapping("/api/myinfo")//存疑
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
