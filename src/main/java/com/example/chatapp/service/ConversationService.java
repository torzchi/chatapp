package com.example.chatapp.service;

import com.example.chatapp.model.Conversation;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.ConversationRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public List<Conversation> getUserConversations(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return conversationRepository.findAllByUsersContaining(user);
    }
    public List<Conversation> getUserConversations(Long userId) {
        List<Conversation> conversations = conversationRepository.findConversationsByUserId(userId);
        // Ensure the conversations are initialized
        conversations.forEach(conversation -> {
            conversation.getUsers().size(); // Force initialization of the users collection
        });
        return conversations;
    }
}
