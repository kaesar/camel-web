package co.onmind.camelweb.controller;

import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {
    
    private final ProducerTemplate producerTemplate;
    
    @PostMapping(produces = "application/json")
    public ResponseEntity<String> processNotification(@RequestBody Map<String, Object> notification) {
        String result = producerTemplate.requestBody("direct:process-notification", notification, String.class);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getFileNotifications() {
        String result = producerTemplate.requestBody("direct:get-file-notifications", null, String.class);
        return ResponseEntity.ok(result);
    }
}