package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
