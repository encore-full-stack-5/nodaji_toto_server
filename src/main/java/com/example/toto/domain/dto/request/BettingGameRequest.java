package com.example.toto.domain.dto.request;

import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.entity.Game;

public record BettingGameRequest(
        Long gameId,
        Integer team
) {
    public BettingGame toEntity(Betting betting, Game game) {
        return BettingGame.builder()
                .bettingId(betting)
                .game(game)
                .team(team)
                .result(0)
                .build();
    }
}
