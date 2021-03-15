package com.example.demo.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "socialnetwork")
public class JwtProperties {

    private String jwtSecret;

    private int jwtExpirationMs;
}
