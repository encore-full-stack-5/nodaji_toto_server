package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;

import java.time.LocalDate;
import java.util.List;

public interface GameService {
    List<GameResponse> getGamesByParam(LocalDate date, String team);
    void insertGame(List<GameRequest> req);
    void updateGameResult(List<GameUpdateRequest> req);
    void deleteGame(Long gameId);
}
