package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    @Override
    public List<GameResponse> getGamesByDate(Date date) {
        return List.of();
    }

    @Override
    public List<GameResponse> getGamesByTeam(String team) {
        return List.of();
    }

    @Override
    public void insertGame(GameRequest req) {

    }

    @Override
    public void updateGameResult(GameUpdateRequest req) {

    }

    @Override
    public void deleteGame(Long gameId) {

    }
}
