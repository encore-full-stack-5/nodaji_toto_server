package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.Betting;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record BettingResponse(
        Long id,
        UUID userId,
        Integer pointAmount,
        LocalDateTime createdAt,
        List<BettingGameResponse> bettingGames
) {
    public static BettingResponse from(Betting betting) {
        return new BettingResponse(
                betting.getBettingId(),
                betting.getUserId(),
                betting.getPointAmount(),
                betting.getCreatedAt(),
                betting.getBettingGames().stream().map(BettingGameResponse::from).toList()
        );
    }
}
