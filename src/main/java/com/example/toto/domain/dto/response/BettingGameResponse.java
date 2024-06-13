package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.BettingGame;

import java.util.Date;

public record BettingGameResponse(
        Long gameId,
        Date gameStartAt,
        String teamHome,
        String teamAway,
        Float gameRtp,
        Long teamId,
        Integer result
) {
    public static BettingGameResponse from(BettingGame bettingGame) {
        return new BettingGameResponse(
                bettingGame.getGame().getGameId(),
                bettingGame.getGame().getGameStartAt(),
                bettingGame.getGame().getTeamHome().getTeamName(),
                bettingGame.getGame().getTeamAway().getTeamName(),
                bettingGame.getGame().getTeamHome().getTeamId().equals(bettingGame.getTeamId())
                        ? bettingGame.getGame().getRtpHome() : bettingGame.getGame().getRtpAway(),
                bettingGame.getTeamId(),
                bettingGame.getResult()
        );
    }
}
