package com.example.toto.domain.dto.request;

import com.example.toto.domain.entity.Betting;

import java.util.List;
import java.util.UUID;

public record BettingRequest(
    Long gameId,
    Long teamId,
    Integer pointAmount,
    List<BettingGameRequest> bettingGames
) {
    public Betting toEntity(UUID userId) {
        return Betting.builder()
                .userId(userId)
                .pointAmount(pointAmount)
                .build();
    }
}
