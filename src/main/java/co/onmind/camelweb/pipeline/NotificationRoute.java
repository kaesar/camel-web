package co.onmind.camelweb.pipeline;

import co.onmind.camelweb.service.FileNotificationReader;
import co.onmind.camelweb.service.NotificationProcessor;
import co.onmind.camelweb.service.FileNotificationWriter;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationRoute extends RouteBuilder {
    
    private final NotificationProcessor notificationProcessor;
    private final FileNotificationReader fileReader;
    private final FileNotificationWriter fileWriter;

    @Override
    public void configure() throws Exception {
        
        // Route for processing notifications
        from("direct:process-notification")
            .routeId("process-notification-pipeline")
            .log("Processing notification: ${body}")
            .bean(notificationProcessor, "processNotification")
            .to("direct:store-file")
            .transform(
                constant("{\"message\": \"Notification processed successfully\", \"status\": \"success\"}"));

        // Route for storing notifications in a file
        from("direct:store-file")
            .routeId("file-storage-pipeline")
            .bean(fileWriter, "appendNotification")
            .log("Notification appended to notifications.json");

        // Route for retrieving notifications from a file
        from("direct:get-file-notifications")
            .routeId("get-file-notifications-pipeline")
            .bean(fileReader, "readAllNotifications");
    }
}