package com.WhiteDeer.controller;


import com.WhiteDeer.entity.Sms;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value="/sms")
public class SmsController {
    @RequestMapping(value="sendcode",method = RequestMethod.POST)
    public void sms(@RequestBody Sms sms){
        int appid= 0;//腾讯云服务的id
        String appkey = "";//腾讯密钥
        int temletId = 0;//短信模板id
        String smsSign ="";//短信签名
        try{
            String[] params={sms.getCode(),Integer.toString((sms.getValid_time()))};
            SmsSingleSender ssender =new SmsSingleSender(appid,appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86",sms.getPhone_number(),temletId,params,smsSign,"","");
        }catch (HTTPException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
