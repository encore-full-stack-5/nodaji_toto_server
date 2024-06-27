package com.example.toto.domain.dto.response;

import com.example.toto.domain.entity.Betting;

import java.time.LocalDateTime;
import java.util.List;

public record BettingResponse(
        Long id,
        Integer pointAmount,
        LocalDateTime createdAt,
        List<BettingGameResponse> bettingGames
) {
    public static BettingResponse from(Betting betting) {
        return new BettingResponse(
                betting.getBettingId(),
                betting.getPointAmount(),
                betting.getCreatedAt(),
                betting.getBettingGames().stream().map(BettingGameResponse::from).toList()
        );
    }
}
