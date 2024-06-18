package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.service.BettingService;
import com.example.toto.service.GameService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@EnableScheduling
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

//    @GetMapping("/set")
    @Scheduled(cron = "0 0/5 * * * *")
    public void createGames() {
        Random rand = new Random();
        List<GameRequest> gameList = new ArrayList<>();
        Map<Integer, Integer> teamOrder = new HashMap<>();
        while(teamOrder.size() < 6) {
            teamOrder.putIfAbsent(rand.nextInt(6), teamOrder.size()+1);
        }

        for (int i=0; i<3; i++) {
            float rtp = rand.nextFloat(1);
            float value = (float) Math.floor((Math.abs(rtp * 2 - 1) * 3 + 1) * 100) / 100;
            gameList.add(new GameRequest(
                    LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(5),
                    LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(4),
                    (long) teamOrder.get(i*2),
                    (long) teamOrder.get(i*2+1),
                    (float) 1 + value * rtp,
                    (float) 1 + value * (1-rtp)
            ));
        }

        gameService.insertGame(gameList);
    }
}
