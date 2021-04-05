package com.example.demo.repository;

import com.example.demo.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN FETCH c.user JOIN FETCH c.user2 WHERE c.user.id = :user1id AND c.user2.id = :user2id")
    Chat findChat(@Param("user1id") Long user1id, @Param("user2id") Long user2id);

    @Query("SELECT c FROM Chat c JOIN FETCH c.user WHERE c.user.id = :userid OR c.user2.id = :userid ORDER BY c.lastMessage DESC")
    List<Chat> findChats(@Param("userid") Long userid);
}
