package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.Game;

import java.time.LocalDateTime;

public record GameResponse (
    Long gameId,
    LocalDateTime gameStartAt,
    LocalDateTime betEndAt,
    Long teamHomeId,
    Long teamAwayId,
    Float rtpHome,
    Float rtpAway,
    Integer result
) {
    public static GameResponse from(Game game){
        return new GameResponse(
                game.getGameId(),
                game.getGameStartAt(),
                game.getBetEndAt(),
                game.getTeamHome().getTeamId(),
                game.getTeamAway().getTeamId(),
                game.getRtpHome(),
                game.getRtpHome(),
                game.getGameResult()
        );
    }
}
