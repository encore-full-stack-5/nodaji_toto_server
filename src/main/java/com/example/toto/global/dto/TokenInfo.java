package com.example.toto.global.dto;

import io.jsonwebtoken.Claims;

import java.util.UUID;

public record TokenInfo(
        UUID userId,
        String name,
        String email
) {
    public static TokenInfo from(Claims claims){
        return new TokenInfo(
                UUID.fromString(claims.get("id", String.class)),
                claims.get("name", String.class),
                claims.get("email", String.class)
        );
    }
}
