package com.example.chatapp.controller;

import com.example.chatapp.DTO.MessageDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.time.Instant;

@Controller
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/conversation/{conversationId}/send-message")
    public void sendMessage(@DestinationVariable Long conversationId, MessageDTO message) {
        // Optionally save the message to the database here

        // Set the timestamp if needed
        message.setTimestamp(Instant.now().toString());

        // Broadcast to all subscribers of the conversation topic
        messagingTemplate.convertAndSend("/topic/conversation/" + conversationId, message);
    }
}
