package com.example.demo.dto;

import java.time.LocalDateTime;

public class ChatDTO {
    private Long id;
    private UserDTO receiver;
    private LocalDateTime lastMessage;
}
