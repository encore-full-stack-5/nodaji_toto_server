package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.domain.repository.TeamRepository;
import jakarta.transaction.Transactional;
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
    public List<GameResponse> getGamesByParam(LocalDate date, Long team) {
        if(date == null) date = LocalDate.now();
        if(team == null) return gameRepository.findGamesByDate(date, date.plusDays(1))
                .stream().map(GameResponse::from).toList();
        return gameRepository.findGamesByDateAndTeam(date, date.plusDays(1), team)
                .stream().map(GameResponse::from).toList();
    }

    @Override
    @Transactional
    public void insertGame(List<GameRequest> req) {
        List<Game> gameList = req.stream().map(e -> e.toEntity(
                teamRepository.findById(e.teamHome()).orElseThrow(IllegalArgumentException::new),
                teamRepository.findById(e.teamAway()).orElseThrow(IllegalArgumentException::new)
        )).toList();
        gameRepository.saveAll(gameList);
    }

    @Override
    @Transactional
    public void updateGameResult(List<GameUpdateRequest> req) {
        req.forEach(e -> {
            Game game = gameRepository.findById(e.gameId()).orElseThrow(IllegalArgumentException::new);
            game.updateResult(e.result());
        });
    }

    @Override
    @Transactional
    public void deleteGame(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(IllegalArgumentException::new);
        gameRepository.delete(game);
    }
}
