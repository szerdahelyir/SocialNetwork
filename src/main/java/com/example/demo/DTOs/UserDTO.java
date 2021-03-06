package com.example.demo.DTOs;

import com.example.demo.models.Image;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    private String description;

    private String location;

    private String email;

    private Long registrationDate;

    private LocalDateTime dob;

    private Image profilePicture;
}
