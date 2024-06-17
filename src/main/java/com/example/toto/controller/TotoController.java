package com.example.toto.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/v1/toto")
@RequiredArgsConstructor
public class TotoController {

    @GetMapping("/test")
    public String getToken() {
        return Jwts.builder()
                .subject("00000000-0000-0000-0000-000000000000")
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor("SecretKeyHereSecretKeyHereSecretKeyHereSecretKeyHere".getBytes()))
                .compact();
    }
}
