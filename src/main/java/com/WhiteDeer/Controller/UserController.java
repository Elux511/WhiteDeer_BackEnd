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

    @GetMapping("/api/myinfo")//存疑
    public Response<UserDTO> getUserById(@RequestParam long id) {
        return Response.newSuccess(userService.getUserById(id));
    }

    @PostMapping("/api/register")
    public Response<Long> createUser(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        return Response.newSuccess(userService.createUser(userDTO));
    }

    @DeleteMapping("/api/deleteuser")
    public void deleteUserById(@RequestParam long id) {
        userService.deleteUserById(id);
    }

    @PatchMapping("/api/changename")
    public void updateNameById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        userService.updateNameById(userDTO);
    }

    @PatchMapping("/api/changephone")
    public void updatePhoneNumberById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        userService.updatePhoneNumberById(userDTO);
    }

    @PatchMapping("/api/setphoto")
    public void updateFaceById(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        userService.updateFaceById(userDTO);
    }
}
