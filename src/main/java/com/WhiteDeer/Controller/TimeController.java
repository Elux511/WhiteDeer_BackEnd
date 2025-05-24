package com.WhiteDeer.Controller;

import com.WhiteDeer.util.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TimeController {

    //获取服务器时间
    @GetMapping("/api/time")
    public Response<Map<String, String>> getServerTime() {
        try {
            String timestamp = ZonedDateTime.now()
                    .format(DateTimeFormatter.ISO_INSTANT);
            Map<String, String> data = new HashMap<>();
            data.put("time", timestamp);
            return Response.newSuccess(1, data);
        } catch (Exception e) {
            Map<String, String> data = new HashMap<>();
            data.put("message", e.getMessage());
            return Response.newFailed(2, data);
        }
    }
}