package com.example.demo.mapper;

import com.example.demo.dto.ChatMessageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    @Mapping(target = "id", source = "chatMessage.id")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "receiver", source = "receiver")
    ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage, UserDTO sender, UserDTO receiver);
}
