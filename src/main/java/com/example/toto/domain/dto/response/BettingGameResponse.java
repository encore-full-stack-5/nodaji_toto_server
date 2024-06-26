package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.BettingGame;

import java.time.LocalDateTime;

public record BettingGameResponse(
        Long gameId,
        LocalDateTime gameStartAt,
        String teamHome,
        String teamAway,
        Float gameRtp,
        Integer team,
        Integer result
) {
    public static BettingGameResponse from(BettingGame bettingGame) {
        return new BettingGameResponse(
                bettingGame.getGame().getGameId(),
                bettingGame.getGame().getGameStartAt(),
                bettingGame.getGame().getTeamHome().getTeamName(),
                bettingGame.getGame().getTeamAway().getTeamName(),
                bettingGame.getTeam() == 1
                        ? bettingGame.getGame().getRtpHome()
                        : bettingGame.getGame().getRtpAway(),
                bettingGame.getTeam(),
                bettingGame.getResult()
        );
    }
}
