package com.example.toto.domain.dto.response;

import java.util.List;
import java.util.Map;

public record GamePageResponse(
        Map<String, Object> pageInfo,
        List<GameResponse> content
) {
}
