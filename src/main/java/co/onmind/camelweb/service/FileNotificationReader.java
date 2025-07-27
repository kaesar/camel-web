package co.onmind.camelweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Files;

@Component
public class FileNotificationReader {
    
    @Value("${notification.storage.path}")
    private String storagePath;
    
    @Value("${notification.storage.file}")
    private String storageFile;
    
    public String readAllNotifications() {
        File file = new File(storagePath, storageFile);
        
        if (file.exists()) {
            try {
                String content = Files.readString(file.toPath());
                return content.isEmpty() ? "[]" : content;
            } catch (Exception e) {
                return "[]";
            }
        }
        
        return "[]";
    }
}