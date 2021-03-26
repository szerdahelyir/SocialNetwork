package com.example.demo.repository;

import com.example.demo.models.ChatMessage;
import com.example.demo.models.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m JOIN FETCH m.sender JOIN FETCH m.receiver " +
            "WHERE m.sender.id = :senderid AND m.receiver.id = :receiverid AND m.status = :status ")
    Long countBySenderIdAndRecipientIdAndStatus(@Param("senderid") Long senderid,
                                                @Param("receiverid") Long receiverid,
                                                @Param("status") MessageStatus status);

    @Query("SELECT m FROM ChatMessage m JOIN FETCH m.sender JOIN FETCH m.receiver JOIN FETCH m.chat " +
            "WHERE m.chat.id = :chatid " +
            "ORDER BY m.creationDate DESC")
    List<ChatMessage> findMessagesByChatId(@Param("chatid") Long chatid);
}
