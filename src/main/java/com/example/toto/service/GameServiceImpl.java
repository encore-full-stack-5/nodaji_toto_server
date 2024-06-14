package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<GameResponse> getGamesByParam(LocalDate date, String team) {
        return List.of();
    }

    @Override
    public void insertGame(List<GameRequest> req) {
        List<Game> gameList = req.stream().map(e -> e.toEntity(
                teamRepository.findById(e.teamHome()).orElseThrow(IllegalArgumentException::new),
                teamRepository.findById(e.teamAway()).orElseThrow(IllegalArgumentException::new)
        )).toList();
        gameRepository.saveAll(gameList);
    }

    @Override
    public void updateGameResult(List<GameUpdateRequest> req) {

    }

    @Override
    public void deleteGame(Long gameId) {

    }
}
