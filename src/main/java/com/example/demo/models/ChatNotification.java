package com.example.demo.models;

import com.example.demo.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatNotification {
    private UserDTO user;
    private Long messageId;
}
