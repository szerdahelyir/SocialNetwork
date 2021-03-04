package com.example.demo.payload.request;


import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @Email
    private String email;

    private Set<String> role;

    private String password;

    private String firstName;

    private String lastName;

    private String gender;

    private String location;

    private LocalDateTime dob;

}
