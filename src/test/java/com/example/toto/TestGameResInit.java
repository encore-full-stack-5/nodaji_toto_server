package com.example.toto;

import com.example.toto.domain.dto.response.GamePageResponse;
import com.example.toto.domain.dto.response.GameResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGameResInit {
    public final GamePageResponse gamePageRes;
    public final Map<String, Object> pageInfo;

    public TestGameResInit() {
        pageInfo = new HashMap<>();
        pageInfo.put("page", 0);
        pageInfo.put("size", 10);
        pageInfo.put("totalElements", 1);
        pageInfo.put("totalPages", 0);
        gamePageRes = new GamePageResponse(
                pageInfo,
                List.of(
                        new GameResponse(
                                1L,
                                LocalDateTime.of(2024,6,15,18,30),
                                LocalDateTime.of(2024,6,15,18,20),
                                1L,
                                2L,
                                "teamA",
                                "teamB",
                                1.5f,
                                2f,
                                0
                        )
                )
        );
    }
}
