package com.WhiteDeer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class SMSServiceImpl implements SMSService {
    private static final String VERIFY_CODE_KEY_PREFIX = "verify_code:";

    @Value("${sms.expire-minutes}")
    private  static int EXPIRE_MINUTES;
    private final RedisTemplate<String, String> redisTemplate;
    private final SecureRandom secureRandom = new SecureRandom();

    public SMSServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 生成并存储验证码到Redis
    @Override
    public String generateAndSaveCode(String phoneNumber) {
        String code = generateCode();
        String key = getRedisKey(phoneNumber);
        redisTemplate.opsForValue().set(
                key,
                code,
                EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );
        return code;
    }

    // 验证验证码并使其立即失效
    @Override
    public boolean verifyCode(String phoneNumber, String code) {
        String key = getRedisKey(phoneNumber);
        String storedCode = redisTemplate.opsForValue().get(key);
        // 验证成功后删除Key
        if (code.equals(storedCode)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyCodeWithoutInvalidate(String phoneNumber, String code) {
        String key = getRedisKey(phoneNumber);
        String storedCode = redisTemplate.opsForValue().get(key);
        return storedCode != null && storedCode.equals(code);
    }

    @Override
    public void invalidateCode(String phoneNumber) {
        String key = getRedisKey(phoneNumber);
        redisTemplate.delete(key);
    }

    //从Redis获取验证码
    @Override
    public String getCodeFromRedis(String phoneNumber) {
        String key = getRedisKey(phoneNumber);
        return redisTemplate.opsForValue().get(key);
    }

    // 生成Redis键
    private String getRedisKey(String phoneNumber) {
        return VERIFY_CODE_KEY_PREFIX + phoneNumber;
    }

    // 生成6位验证码
    private String generateCode() {
        int randomNum = secureRandom.nextInt(1000000);
        return String.format("%06d", randomNum);
    }

}