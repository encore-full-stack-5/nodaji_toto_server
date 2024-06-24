package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameDetailResponse;
import com.example.toto.domain.dto.response.GamePageResponse;
import com.example.toto.domain.dto.response.GameResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface GameService {
    GamePageResponse getGamesByParam(LocalDate date, Long team, Integer page, Integer pageSize);
    List<GameDetailResponse> getGameDetailsById(Long gameId);
    void insertGame(List<GameRequest> req);
    void updateGameResult(List<GameUpdateRequest> req);
    void deleteGame(Long gameId);
}
