package com.example.demo.payload.request;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @Email
    @NotBlank
    private String email;

    private Set<String> role;

    @NotBlank
    private String password;

}
