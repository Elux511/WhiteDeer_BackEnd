package com.WhiteDeer.Controller;

import com.WhiteDeer.util.Response;
import com.WhiteDeer.dto.SMSDTO;
import com.WhiteDeer.service.SMSService;
import com.WhiteDeer.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SMSController {

    @Value("${sms.host}")
    private String HOST;
    @Value("${sms.path}")
    private String PATH;
    @Value("${sms.method}")
    private String METHOD;
    @Value("${sms.appcode}")
    private String APPCODE;
    @Value("${sms.sign-id}")
    private String SMS_SIGN_ID;
    @Value("${sms.template-id}")
    private String TEMPLATE_ID;
    @Value("${sms.expire-minutes}")
    private int EXPIRE_MINUTES;

    @Autowired
    private SMSService smsService;

    @PostMapping("/api/vericode")
    public Response<Map<String, Object>> sendCode(@RequestParam String phoneNumber) {
        Map<String, Object> result = new HashMap<>();
        try {
            //构建请求参数
            String code = smsService.generateAndSaveCode(phoneNumber);
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + APPCODE);
            Map<String, String> querys = new HashMap<>();
            querys.put("mobile", phoneNumber); // 目标手机号
            querys.put("param", String.format("**code**:%s,**minute**:%d", code, EXPIRE_MINUTES)); // 模板变量
            querys.put("smsSignId", SMS_SIGN_ID); // 签名ID
            querys.put("templateId", TEMPLATE_ID); // 模板ID
            Map<String, String> bodys = new HashMap<>(); // 表单参数（留空）
            //调用 HttpUtils 发送POST请求
            HttpResponse response = HttpUtils.doPost(HOST, PATH, METHOD, headers, querys, bodys);
            //解析响应结果
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity()); // 获取响应体
            if (statusCode == 200) {
                result.put("success", true);
                result.put("message", "短信发送成功");
                result.put("response", responseBody);
                return Response.newSuccess(1, result);
            } else {
                result.put("success", false);
                result.put("message", "短信发送失败，状态码：" + statusCode);
                result.put("response", responseBody);
                return Response.newFailed(2, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常：" + e.getMessage());
            return Response.newFailed(3, result);
        }
    }

    @PostMapping("/api/checkvericode")
    public Response<Map<String, Object>> checkCode(@RequestBody @Validated SMSDTO smsdto) {
        Map<String, Object> result = new HashMap<>();
        try {
            String phoneNumber = smsdto.getPhoneNumber();
            String code = smsdto.getVericode();
            boolean isValid = smsService.verifyCodeWithoutInvalidate(phoneNumber, code);
            if (isValid) {
                smsService.invalidateCode(phoneNumber);
                result.put("state", 1);
                result.put("message", "验证通过");
                return Response.newSuccess(1, result);
            } else {
                String storedCode = smsService.getCodeFromRedis(phoneNumber);
                if (storedCode == null) {
                    result.put("message", "验证码不存在或已过期");
                    return Response.newFailed(3, result);
                } else {
                    result.put("message", "验证码错误");
                    return Response.newFailed(2, result);
                }
            }

        } catch (Exception e) {
            result.put("message", "未知错误验证码验证失败");
            result.put("message", e.getMessage());
            return Response.newFailed(4, result);
        }
    }
}