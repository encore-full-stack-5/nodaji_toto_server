package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.BettingGame;

public record BettingGameResponse(
        Long gameId,
        Long teamId,
        Integer result
) {
    public static BettingGameResponse from(BettingGame bettingGame) {
        return new BettingGameResponse(
                bettingGame.getBettingGameId(),
                bettingGame.getTeamId(),
                bettingGame.getResult()
        );
    }
}
