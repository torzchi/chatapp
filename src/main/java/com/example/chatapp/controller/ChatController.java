package com.example.chatapp.controller;

import com.example.chatapp.model.Conversation;
import com.example.chatapp.model.CreateConversationRequest;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.ConversationRepository;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatController(ConversationRepository conversationRepository,
                          UserRepository userRepository,
                          MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    // Get or create a conversation between two users

    // Get all messages for a specific conversation
    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<Message>> getMessagesForConversation(
            @PathVariable Long conversationId) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);

        if (conversation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Message> messages = messageRepository.findByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }

    // Send a message to a specific conversation
    @PostMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long conversationId,
            @RequestBody Message message) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);

        if (conversation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        message.setConversation(conversation.get());
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    // Get a specific conversation by ID
    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<Conversation> getConversation(@PathVariable Long conversationId) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        return conversation
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/api/users/by-username/{username}")
    public ResponseEntity<Long> getUserId(@PathVariable String username) {
        System.out.println("Looking up user ID for username: " + username);
        Long userId = userRepository.findIdByUsername(username);
        if (userId == null) {
            System.out.println("No user found with username: " + username);
            return ResponseEntity.notFound().build();
        }
        System.out.println("Found user ID: " + userId);
        return ResponseEntity.ok(userId);
    }
    @GetMapping("/users/{userId}/conversations")
    public ResponseEntity<List<Conversation>> getUserConversations(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Conversation> conversations = conversationRepository.findByUsersContaining(user.get());
            return ResponseEntity.ok(conversations);
        }
        return ResponseEntity.notFound().build();
    }




    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(@RequestBody CreateConversationRequest request) {
        Set<User> users = request.getUserIds().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        if (users.size() < 2) {
            return ResponseEntity.badRequest().build(); // Ensure at least two participants
        }


        // Check for existing conversation with the same users
        List<Conversation> existingConversations = conversationRepository.findConversationsWithExactUsers(users, users.size());
        if (!existingConversations.isEmpty()) {
            return ResponseEntity.ok(existingConversations.get(0));
        }

        Conversation conversation = new Conversation();
        conversation.setUsers(users);



        // Generate a fallback name if no explicit name is provided
        String conversationName = users.stream()
                .map(User::getUsername)
                .collect(Collectors.joining(", "));
        conversation.setName(conversationName);

        System.out.println("Users in conversation: " + users); // Log participants
        System.out.println("Conversation saved: " + conversation); // Log saved conversation

        return ResponseEntity.ok(conversationRepository.save(conversation));
    }



}