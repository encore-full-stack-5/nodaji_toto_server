package com.example.toto.service;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameDetailResponse;
import com.example.toto.domain.dto.response.GamePageResponse;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.domain.repository.TeamRepository;
import com.example.toto.exception.NotFoundException;
import com.example.toto.utils.ResultValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final ResultValidationUtils resultValidationUtils;

    @Override
    public GamePageResponse getGamesByParam(LocalDate date, Long team, Integer page, Integer pageSize) {
        if(date == null) date = LocalDate.now();
        Page<Game> content;
        if(team == null) {
            content = gameRepository.findAllGamesByDate(date, date.plusDays(1), PageRequest.of(page, pageSize));
        } else {
            content = gameRepository.findAllGamesByDateAndTeam(date, date.plusDays(1), team, PageRequest.of(page, pageSize));
        }
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("page", page);
        pageInfo.put("size", pageSize);
        pageInfo.put("totalElements", content.getTotalElements());
        pageInfo.put("totalPages", content.getTotalPages());
        return new GamePageResponse(pageInfo, content.stream().map(GameResponse::from).toList());
    }

    @Override
    public List<GameDetailResponse> getGameDetailsById(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException(("GAME")));
        return game.getTeamHome().getHomeGame().stream()
                .filter(e ->
                        e.getTeamAway() == game.getTeamAway() &&
                        e.getGameResult() != 0)
                .map(GameDetailResponse::from)
                .toList();
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
