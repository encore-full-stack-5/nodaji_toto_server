package com.example.toto.global.utils;

import com.example.toto.global.dto.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
    private final SecretKey secretKey;

    public String validationToken(String token) {
        String[] tokenSplit = token.split(" ");
        if(tokenSplit.length != 2) throw new DecodingException("FORMAT IS NOT CORRECT");
        if(!tokenSplit[0].equals("Bearer")) throw new DecodingException("IS NOT BEARER TOKEN");
        if(tokenSplit[1].split("\\.").length != 3) throw  new DecodingException("IS NOT VALID TOKEN");
        return tokenSplit[1];
    }

    public TokenInfo parseToken(String token) {
        String checkedToken = validationToken(token);
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(checkedToken)
                .getPayload();
        return TokenInfo.from(payload);
    }

    public JwtUtils(@Value("${token.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
}
