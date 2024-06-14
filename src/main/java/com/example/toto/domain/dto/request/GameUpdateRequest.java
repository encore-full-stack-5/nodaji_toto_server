package com.example.toto.domain.dto.request;

public record GameUpdateRequest(
        Long gameId,
        Integer result
) {
}
