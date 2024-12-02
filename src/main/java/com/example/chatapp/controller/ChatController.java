package com.example.chatapp.controller;

import com.example.chatapp.model.Message;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(Message chatMessage) {
        // Save message to database
        messageRepository.save(chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(Message chatMessage) {
        chatMessage.setType(Message.MessageType.JOIN);
        // Save message to database
        messageRepository.save(chatMessage);

        return chatMessage;
    }

    // Endpoint to fetch only 'CHAT' messages
    @RestController
    public class MessageController {

        private final MessageRepository messageRepository;

        public MessageController(MessageRepository messageRepository) {
            this.messageRepository = messageRepository;
        }

        @GetMapping("/api/messages/chat")
        public List<Message> getMessages() {
            return messageRepository.findByType(Message.MessageType.CHAT);
        }
    }
}
