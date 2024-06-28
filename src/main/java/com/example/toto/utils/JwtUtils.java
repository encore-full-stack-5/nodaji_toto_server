package com.example.toto.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
    private final SecretKey secretKey;

    public String validationToken(String token) {
        String[] tokenSplit = token.split(" ");
        if(tokenSplit.length != 2) throw new JwtException("IS NOT BEARER TOKEN");
        if(tokenSplit[1].split("\\.").length != 3) throw  new JwtException("IS NOT VALID TOKEN");
        return tokenSplit[1];
    }

    public String parseToken(String token) {
        String checkedToken = validationToken(token);
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(checkedToken)
                .getPayload();
        return payload.getSubject();
    }

    public JwtUtils(@Value("${token.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
}
