package com.example.toto.controller;

import com.example.toto.global.api.FeignPayment;
import com.example.toto.global.dto.request.UserPaymentRequest;
import com.example.toto.global.dto.request.UserWinRequest;
import com.example.toto.global.dto.response.UserPaymentResponse;
import com.example.toto.global.dto.response.UserPointResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/toto")
@RequiredArgsConstructor
public class DevTestController {
    private final FeignPayment feignPayment;

    @GetMapping("/token")
    public String getToken() {
        return Jwts.builder()
                .subject("00000000-0000-0000-0000-000000000000")
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor("SecretKeyHereSecretKeyHereSecretKeyHereSecretKeyHere".getBytes()))
                .compact();
    }

    @GetMapping("/accounts/{userId}")
    public UserPointResponse apiTest1(@PathVariable String userId) {
        return feignPayment.getPointByUserId(userId);
    }

    @PostMapping("/payments/{userId}")
    public UserPaymentResponse apiTest2(
            @PathVariable String userId,
            @RequestBody UserPaymentRequest req
    ) {
        return feignPayment.payTotoByUser(userId, req);
    }

    @PostMapping("/win/{userId}")
    public void apiTest2(
            @PathVariable String userId,
            @RequestBody UserWinRequest req
    ) {
        feignPayment.sendWinUser(userId, req);
    }
}
