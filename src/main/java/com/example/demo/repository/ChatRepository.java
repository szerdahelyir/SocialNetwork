package com.example.demo.repository;

import com.example.demo.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.user.id = :user1id AND c.user2.id = :user2id")
    Chat findChat(@Param("user1id") Long user1id, @Param("user2id") Long user2id);
}
