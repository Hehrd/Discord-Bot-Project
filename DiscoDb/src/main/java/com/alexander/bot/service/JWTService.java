package com.alexander.bot.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    private final String ISSUER = "DiscoChanDb";

    private final String secretKey;

    @Autowired
    public JWTService(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String createByDiscordId(String id) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                        .withIssuer(ISSUER)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                        .withSubject(id)
                        .sign(algorithm);
        return token;
    }
}
