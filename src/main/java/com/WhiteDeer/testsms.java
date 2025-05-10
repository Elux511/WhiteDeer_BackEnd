package com.WhiteDeer;
import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniSMS;
import com.apistd.uni.sms.UniMessage;

import java.util.HashMap;
import java.util.Map;

public class testsms {
    public static String ACCESS_KEY_ID = "n6FZiNuMEmdocAAuoDyjBmgWEh7cehKchPoX38aXcTrizovhJ";
    private static String ACCESS_KEY_SECRET = "";

    public static void main(String[] args) {
        // 初始化
        Uni.init(ACCESS_KEY_ID); // 若使用简易验签模式仅传入第一个参数即可

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", "6666");
        templateData.put("ttl", "5");

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo("15625153275")
                .setSignature("")
                .setTemplateId("pub_verif_ttl")
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
    }
}
