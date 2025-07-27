package co.onmind.camelweb.service;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class NotificationProcessor {
    
    public Map<String, Object> processNotification(Map<String, Object> body) {
        String message = (String) body.get("message");
        
        // Prepare the notification data
        return Map.of(
            "message", message,
            "timestamp", LocalDateTime.now().toString()
        );
    }
}