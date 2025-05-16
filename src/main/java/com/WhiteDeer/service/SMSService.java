package com.WhiteDeer.service;

import org.springframework.stereotype.Service;

@Service
public interface SMSService {

    // 生成并存储验证码到Redis
    String generateAndSaveCode(String phoneNumber);

    // 验证验证码并使其立即失效
    boolean verifyCode(String phoneNumber, String code);


}
