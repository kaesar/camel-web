package co.onmind.camelweb.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FileNotificationWriter {
    
    @Value("${notification.storage.path}")
    private String storagePath;
    
    @Value("${notification.storage.file}")
    private String storageFile;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public void appendNotification(Map<String, Object> notification) {
        List<Map<String, Object>> notifications = new ArrayList<>();
        File file = new File(storagePath, storageFile);
        
        // Create directories if they do not exist
        file.getParentFile().mkdirs();
        
        // Read existing notifications
        if (file.exists()) {
            try {
                String content = Files.readString(file.toPath());
                if (!content.trim().isEmpty()) {
                    notifications = objectMapper.readValue(content, new TypeReference<List<Map<String, Object>>>(){});
                }
            } catch (Exception e) {
                // Ignore errors reading the file but log them
                System.err.println("Error reading notifications file: " + e.getMessage());
            }
        }
        
        // Add new notification
        notifications.add(notification);
        
        // Write back to file
        try {
            String json = objectMapper.writeValueAsString(notifications);
            Files.writeString(file.toPath(), json);
        } catch (Exception e) {
            throw new RuntimeException("Error writing notification to file", e);
        }
    }
}