package com.example.toto.domain.dto.request;

import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.entity.Game;

public record BettingGameRequest(
        Long gameId,
        Long teamId
) {
    public BettingGame toEntity(Betting betting, Game game) {
        return BettingGame.builder()
                .bettingId(betting)
                .game(game)
                .teamId(teamId)
                .build();
    }
}
