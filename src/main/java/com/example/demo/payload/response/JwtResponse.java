package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private Long id;
    private String email;
    private List<String> roles;
}
