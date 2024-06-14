package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.domain.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public List<GameResponse> getGamesByParam(LocalDate date, String team) {
        return List.of();
    }

    @Override
    public void insertGame(List<GameRequest> req) {

    }

    @Override
    public void updateGameResult(List<GameUpdateRequest> req) {

    }

    @Override
    public void deleteGame(Long gameId) {

    }
}
