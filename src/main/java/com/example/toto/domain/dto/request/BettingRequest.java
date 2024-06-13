package com.example.toto.domain.dto.request;

import com.example.toto.domain.entity.Betting;

import java.util.UUID;

public record BettingRequest(
    Long gameId,
    Long teamId,
    Integer pointAmount
) {
    public Betting toEntity(UUID userId) {
        return Betting.builder()
                .userId(userId)
                .gameId(gameId)
                .teamId(teamId)
                .pointAmount(pointAmount)
                .build();
    }
}
