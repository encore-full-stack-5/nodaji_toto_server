package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.service.BettingService;
import com.example.toto.service.GameService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/toto")
@RequiredArgsConstructor
public class TotoController {
    private final BettingService bettingService;
    private final GameService gameService;

    @GetMapping("/test")
    public String getToken() {
        return Jwts.builder()
                .subject("00000000-0000-0000-0000-000000000000")
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor("SecretKeyHereSecretKeyHereSecretKeyHereSecretKeyHere".getBytes()))
                .compact();
    }

    @PutMapping("/set-result")
    public void setResultGame(@RequestBody GameUpdateRequest request) {
        gameService.updateGameResult(List.of(request));
        bettingService.updateBettingResult(request);
    }
}
