package com.example.demo.dto;

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

    private LocalDateTime registrationDate;

    private LocalDateTime dob;

    private ImageDTO profilePicture;

    private Integer relationship;
}
