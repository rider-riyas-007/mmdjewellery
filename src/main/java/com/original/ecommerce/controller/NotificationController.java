package com.original.ecommerce.controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class NotificationController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    public void sendOrderNotification(String message) {
        Map<String, String> notification = new HashMap<>();
        notification.put("content", message);
        notification.put("timestamp", LocalDateTime.now().toString());
        
        messagingTemplate.convertAndSend("/topic/orders", notification);
    }
}