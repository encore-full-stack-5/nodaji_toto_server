package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.Game;

import java.time.LocalDateTime;

public record GameDetailResponse(
        Long gameId,
        Integer result,
        LocalDateTime startAt
) {
    public static GameDetailResponse from(Game game) {
        return new GameDetailResponse(
                game.getGameId(),
                game.getGameResult(),
                game.getGameStartAt()
        );
    }
}
