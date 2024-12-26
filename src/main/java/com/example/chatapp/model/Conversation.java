package com.example.chatapp.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_conversation",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Conversation() {
        this.users = new HashSet<>();
    }
}
