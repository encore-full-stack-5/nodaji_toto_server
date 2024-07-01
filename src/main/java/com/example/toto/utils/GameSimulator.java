package com.example.toto.utils;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.service.BettingService;
import com.example.toto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

// test simulation
@Component
@RequiredArgsConstructor
@EnableScheduling
public class GameSimulator {
    private final GameService gameService;
    private final BettingService bettingService;
    private final GameRepository gameRepository;

    @Scheduled(cron = "0 0/5 * * * *")
    public void createGames() {
        int teamNum = 14;
        int gameCount = 4;
        Random rand = new Random();
        List<GameRequest> gameList = new ArrayList<>();
        Map<Integer, Integer> teamOrder = new HashMap<>();
        while(teamOrder.size() < teamNum) {
            teamOrder.putIfAbsent(rand.nextInt(teamNum), teamOrder.size()+1);
        }

        for (int i=0; i<gameCount; i++) {
            float rtp = rand.nextFloat(0.8f)+0.1f;
            gameList.add(new GameRequest(
                    LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(10),
                    LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(5),
                    (long) teamOrder.get(i*2),
                    (long) teamOrder.get(i*2+1),
                    (float) Math.ceil(Math.pow((rtp * 1.6), 3) * 100) / 100 + 1,
                    (float) Math.ceil(Math.pow(((1-rtp) * 1.6), 3) * 100) / 100 + 1
            ));
        }
        gameService.insertGame(gameList);
    }

    @Scheduled(cron = "0 0/5 * * * *")
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
        });
    }
}
