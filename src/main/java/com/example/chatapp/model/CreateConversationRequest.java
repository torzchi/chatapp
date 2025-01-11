package com.example.chatapp.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateConversationRequest {
    private List<Long> userIds;
}