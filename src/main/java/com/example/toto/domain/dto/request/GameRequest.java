package com.example.toto.domain.dto.request;

import com.example.toto.domain.entity.Game;
import com.example.toto.domain.entity.Team;

import java.time.LocalDateTime;

public record GameRequest(
        LocalDateTime startAt,
        LocalDateTime betEndAt,
        Long teamHome,
        Long teamAway,
        Float rtpHome,
        Float rtpAway
) {
    public Game toEntity(Team home, Team away) {
        return Game.builder()
                .gameStartAt(startAt)
                .betEndAt(betEndAt)
                .teamHome(home)
                .teamAway(away)
                .rtpHome(rtpHome)
                .rtpAway(rtpAway)
                .build();
    }
}
