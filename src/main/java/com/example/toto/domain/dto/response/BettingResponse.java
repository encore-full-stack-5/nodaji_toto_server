package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.Betting;

public record BettingResponse(
        Long gameId,
        Long teamId,
        Integer bettingPointAmount,
        Integer bettingResult
) {
    public static BettingResponse from(Betting betting) {
        return new BettingResponse(
                betting.getGameId(),
                betting.getTeamId(),
                betting.getPointAmount(),
                betting.getBettingResult()
        );
    }
}
