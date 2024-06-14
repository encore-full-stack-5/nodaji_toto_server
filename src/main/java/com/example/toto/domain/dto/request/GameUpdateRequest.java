package com.example.toto.domain.dto.request;

import com.example.toto.domain.entity.Game;

public record GameUpdateRequest(
        Long gameId,
        Integer result
) {
}
