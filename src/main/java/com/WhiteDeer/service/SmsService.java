package com.WhiteDeer.service;

import com.WhiteDeer.entity.User;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 注入配置信息
    private int appid = 0;
    private String appkey = "";
    private int templateId = 0;
    private String smsSign = "";
    private static final String SMS_CODE_PREFIX = "sms:code:";
    private static final int VALID_TIME_MINUTES = 5;

    public void sendVerificationCode(User user) {
        String phoneNumber = user.getPhoneNumber();
        String redisKey = SMS_CODE_PREFIX + phoneNumber;

        // 检查是否已发送且未过期
        if (redisTemplate.hasKey(redisKey)) {
            throw new RuntimeException("验证码发送过于频繁，请稍后再试");
        }

        // 生成验证码
        String code = generateCode();

        // 保存到Redis，设置5分钟过期
        redisTemplate.opsForValue().set(redisKey, code, VALID_TIME_MINUTES, TimeUnit.MINUTES);

        // 发送短信
        try {
            String[] params = {code, Integer.toString(VALID_TIME_MINUTES)};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam(
                    "86", phoneNumber, templateId, params, smsSign, "", "");

            if (result.result != 0) {
                // 发送失败，删除Redis中的记录
                redisTemplate.delete(redisKey);
                throw new RuntimeException("短信发送失败: " + result.errMsg);
            }
        } catch (HTTPException | JSONException | IOException e) {
            // 发送异常，删除Redis中的记录
            redisTemplate.delete(redisKey);
            throw new RuntimeException("短信发送异常", e);
        }
    }

    // 验证验证码方法
    public boolean verifyCode(String phoneNumber, String inputCode) {
        String redisKey = SMS_CODE_PREFIX + phoneNumber;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode != null && storedCode.equals(inputCode)) {
            // 验证通过后删除验证码
            redisTemplate.delete(redisKey);
            return true;
        }
        return false;
    }

    // 生成6位数字验证码
    private String generateCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }
}