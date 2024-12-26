package com.example.chatapp.repository;

import com.example.chatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username); //

    @Query(value = "SELECT id FROM users WHERE username = :username", nativeQuery = true)
    Long findIdByUsername(@Param("username") String username);
}

