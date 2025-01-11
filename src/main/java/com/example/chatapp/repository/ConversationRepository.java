package com.example.chatapp.repository;

import com.example.chatapp.model.Conversation;
import com.example.chatapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    // Custom query to find a conversation between two users
    @Query("SELECT c FROM Conversation c WHERE :user1 MEMBER OF c.users AND :user2 MEMBER OF c.users")
    Optional<Conversation> findByUsers(User user1, User user2);

    List<Conversation> findAllByUsersContaining(User user);

    @Query("SELECT c FROM Conversation c JOIN c.users u WHERE u = :user")
    List<Conversation> findByUsersContaining(@Param("user") User user);
}
