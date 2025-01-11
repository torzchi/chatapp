package com.example.chatapp.controller;

import com.example.chatapp.model.Conversation;
import com.example.chatapp.model.User;
import com.example.chatapp.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/conversationss")
public class ConversationController {

    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // Fetch all conversations for the logged-in user
    @GetMapping
    public ResponseEntity<List<Conversation>> getUserConversations(Principal principal) {
        String username = principal.getName();
        List<Conversation> conversations = conversationService.getUserConversations(username);
        return ResponseEntity.ok(conversations);
    }
}
