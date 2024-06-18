package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.domain.repository.TeamRepository;
import com.example.toto.exception.NotFoundException;
import com.example.toto.utils.ResultValidationUtils;
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
    private final ResultValidationUtils resultValidationUtils;

    @Override
    public List<GameResponse> getGamesByParam(LocalDate date, Long team) {
        if(date == null) date = LocalDate.now();
        if(team == null) return gameRepository.findAllGamesByDate(date, date.plusDays(1))
                .stream().map(GameResponse::from).toList();
        return gameRepository.findAllGamesByDateAndTeam(date, date.plusDays(1), team)
                .stream().map(GameResponse::from).toList();
    }

    @Override
    @Transactional
    public void insertGame(List<GameRequest> req) {
        List<Game> gameList = req.stream().map(e -> e.toEntity(
                teamRepository.findById(e.teamHome()).orElseThrow(() -> new NotFoundException("TEAM")),
                teamRepository.findById(e.teamAway()).orElseThrow(() -> new NotFoundException("TEAM"))
        )).toList();
        gameRepository.saveAll(gameList);
    }

    @Override
    @Transactional
    public void updateGameResult(List<GameUpdateRequest> req) {
        req.forEach(e -> {
            Game game = gameRepository.findById(e.gameId()).orElseThrow(() -> new NotFoundException("GAME"));
            resultValidationUtils.gameResultValidation(e.result());
            game.updateResult(e.result());
        });
    }

    @Override
    @Transactional
    public void deleteGame(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("GAME"));
        gameRepository.delete(game);
    }
}
