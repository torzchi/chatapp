package com.example.chatapp.repository;

import com.example.chatapp.model.Conversation;
import com.example.chatapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    // Custom query to find a conversation between two users
    @Query("SELECT c FROM Conversation c WHERE :user1 MEMBER OF c.users AND :user2 MEMBER OF c.users")
    Optional<Conversation> findByUsers(User user1, User user2);

    List<Conversation> findAllByUsersContaining(User user);

    @Query("SELECT c FROM Conversation c JOIN c.users u WHERE u = :user")
    List<Conversation> findByUsersContaining(@Param("user") User user);

    @Query("SELECT c FROM Conversation c " +
                  "WHERE (SELECT COUNT(DISTINCT u) FROM c.users u) = :userCount " +
                  "AND NOT EXISTS (SELECT u FROM c.users u WHERE u NOT IN :users) " +
                  "AND NOT EXISTS (SELECT u FROM User u WHERE u IN :users AND u NOT IN (SELECT us FROM c.users us))")
    List<Conversation> findConversationsWithExactUsers(@Param("users") Set<User> users, @Param("userCount") long userCount);

    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.users " +  // Add JOIN FETCH to load users
            "WHERE c IN (SELECT conv FROM Conversation conv JOIN conv.users u WHERE u.id = :userId)")
    List<Conversation> findConversationsByUserId(@Param("userId") Long userId);
}


