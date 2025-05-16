package com.WhiteDeer.service;

import org.springframework.stereotype.Service;

@Service
public interface SMSService {

    // 生成并存储验证码到Redis
    String generateAndSaveCode(String phoneNumber);

    // 验证验证码并使其立即失效
    boolean verifyCode(String phoneNumber, String code);

    //验证验证码且不失效
    boolean verifyCodeWithoutInvalidate(String phoneNumber, String code);

    //使验证码失效
    void invalidateCode(String phoneNumber);

    //从Redis获取验证码
    String getCodeFromRedis(String phoneNumber);

}
