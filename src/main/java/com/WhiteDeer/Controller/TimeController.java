import com.WhiteDeer.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TimeController {

    //获取服务器时间
    @GetMapping("/api/time")
    public ResponseEntity<Response<Map<String, String>>> getServerTime() {
        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ISO_INSTANT);
            Map<String, String> data = new HashMap<>();
            data.put("time", timestamp);
            return ResponseEntity.ok(Response.newSuccess(1, data));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Response.newFailed(2, null));
        }
    }
}