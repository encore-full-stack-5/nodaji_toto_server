package com.example.toto.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
    @Value(("${token.expiration}"))
    private Long tokenExpiration;
    @Value("${token.secret}")
    private SecretKey secretKey;

    public String parseToken(String token) {
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();
        return payload.getSubject();
    }
}
