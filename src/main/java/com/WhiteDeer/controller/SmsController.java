package com.WhiteDeer.controller;

import com.WhiteDeer.entity.User;
import com.WhiteDeer.service.SmsService;
import com.WhiteDeer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sendcode/{userId}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable Long userId) {
        // 从数据库加载用户
        Optional<User> userOptional = userRepository.findById(Math.toIntExact(userId));

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        // 检查用户是否已设置手机号
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("用户未设置手机号");
        }

        try {
            smsService.sendVerificationCode(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}