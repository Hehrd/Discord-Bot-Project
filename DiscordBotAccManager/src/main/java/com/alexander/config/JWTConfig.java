package com.alexander.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {
    @Bean
    public JWTVerifier verifier(@Value("${jwt-secret-key}") String secretKey) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("DiscoChanDb")
                .build();
    }
}
