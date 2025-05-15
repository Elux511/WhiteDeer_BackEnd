package com.WhiteDeer.Controller;

import com.WhiteDeer.Response;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SMSController {

    private static final String HOST = "https://gyytz.market.alicloudapi.com";
    private static final String PATH = "/sms/smsSend";
    private static final String METHOD = HttpPost.METHOD_NAME;
    private static final String APPCODE = "8bb9530e92f3416fa3e76c5991f9747e"; // 阿里云AppCode
    private static final String SMS_SIGN_ID = "2e65b1bb3d054466b82f0c9d125465e2"; //国阳云默认测试id
    private static final String TEMPLATE_ID = "908e94ccf08b4476ba6c876d13f084ad"; //国阳云默认测试模板

    @PostMapping("/api/vericode")
    public Response<Map<String, Object>> sendCode(@RequestParam String phoneNumber) {
        Map<String, Object> result = new HashMap<>();
        try {
            //构建请求参数
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + APPCODE);
            Map<String, String> querys = new HashMap<>();
            querys.put("mobile", phoneNumber); // 目标手机号
            querys.put("param", "**code**:12345,**minute**:5"); // 模板变量
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
}