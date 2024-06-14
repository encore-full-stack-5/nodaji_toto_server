package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;

import java.util.Date;
import java.util.List;

public interface GameService {
    List<GameResponse> getGamesByDate(Date date);
    List<GameResponse> getGamesByTeam(String team);
    void insertGame(GameRequest req);
    void updateGameResult(GameUpdateRequest req);
    void deleteGame(Long gameId);
}
