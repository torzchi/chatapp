package com.example.chatapp.controller;

import com.example.chatapp.model.Conversation;
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
import java.util.logging.Logger;

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
    @GetMapping("/conversations")
    public ResponseEntity<Conversation> getConversationBetweenUsers(
            @RequestParam String user1,
            @RequestParam String user2) {

        Logger logger = Logger.getLogger(ChatController.class.getName());

        System.out.println(user1  + " "  + user2);

        Optional<User> user1Opt = Optional.ofNullable(userRepository.findByUsername(user1));
        Optional<User> user2Opt = Optional.ofNullable(userRepository.findByUsername(user2));
        System.out.println(user1Opt.isPresent()  + " "  + user2Opt.isPresent());

        if (user1Opt.isEmpty() || user2Opt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        User userOne = user1Opt.get();
        User userTwo = user2Opt.get();

        Optional<Conversation> existingConversation = conversationRepository.findByUsers(userOne, userTwo);

        if (existingConversation.isPresent()) {
            return ResponseEntity.ok(existingConversation.get());
        }

        Conversation conversation = new Conversation();
        conversation.setName("Private conversation between " + userOne.getUsername() + " and " + userTwo.getUsername());
        conversation.getUsers().add(userOne);
        conversation.getUsers().add(userTwo);

        Conversation savedConversation = conversationRepository.save(conversation);
        return ResponseEntity.ok(savedConversation);
    }

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


}