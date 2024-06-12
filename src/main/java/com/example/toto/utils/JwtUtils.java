package com.example.toto.utils;

import com.example.toto.domain.dto.TokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    @Value(("${token.expiration}"))
    private Long tokenExpiration;
    @Value("${token.secret}")
    private String tokenSecret;

    public TokenInfo parseToken(String token) {
        return null;
    }
}
