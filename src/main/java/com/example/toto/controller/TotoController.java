package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.service.BettingService;
import com.example.toto.service.GameService;
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
    private final GameRepository gameRepository; // for test simulation

    @PutMapping("/set-result")
    public void setResultGame(@RequestBody GameUpdateRequest request) {
        gameService.updateGameResult(List.of(request));
        bettingService.updateBettingResult(request);
    }

    // test simulation method
    @GetMapping("/set1")
//    @Scheduled(cron = "0 0/2 * * * *")
    public void createGames() {
        Random rand = new Random();
        List<GameRequest> gameList = new ArrayList<>();
        Map<Integer, Integer> teamOrder = new HashMap<>();
        while(teamOrder.size() < 6) {
            teamOrder.putIfAbsent(rand.nextInt(6), teamOrder.size()+1);
        }

        for (int i=0; i<3; i++) {
            float rtp = rand.nextFloat(0.8f)+0.1f;
            gameList.add(new GameRequest(
                    LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(2),
                    LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(1),
                    (long) teamOrder.get(i*2),
                    (long) teamOrder.get(i*2+1),
                    (float) Math.ceil(Math.pow((rtp * 1.6), 3) * 100) / 100 + 1,
                    (float) Math.ceil(Math.pow(((1-rtp) * 1.6), 3) * 100) / 100 + 1
            ));
        }

        gameService.insertGame(gameList);
    }

    // test simulation method
    @GetMapping("/set2")
//    @Scheduled(cron = "0 0/2 * * * *")
    public void resultGames() {
        List<Game> allGamesByResult = gameRepository.findAllGamesByGameResultAndGameStartAtBefore(0, LocalDateTime.now());
        allGamesByResult.forEach(e -> {
                    double rand = new Random().nextDouble(1.1);
                    int result; // 1:홈팀승, 2:원정팀승, 3:무승부, 4:경기취소
                    if(rand > 1.07) {
                        result = 4;
                    } else if(rand > 1) {
                        result = 3;
                    } else {
                        result = Math.cbrt(e.getRtpHome() - 1) / 1.6 < rand ? 1 : 2;
                    }
                    GameUpdateRequest gameUpdateRequest = new GameUpdateRequest(e.getGameId(), result);
                    gameService.updateGameResult(List.of(gameUpdateRequest));
                    bettingService.updateBettingResult(gameUpdateRequest);
                }
        );
    }
}
