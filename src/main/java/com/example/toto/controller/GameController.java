package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/toto/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping
    public List<GameResponse> getGames(
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "team", required = false) Long team
    ) {
        return date == null
                ? gameService.getGamesByParam(LocalDate.now(), team)
                : gameService.getGamesByParam(date, team);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addGame(@RequestBody List<GameRequest> req) {
        gameService.insertGame(req);
    }

    @PutMapping
    public void updateGameResult(@RequestBody List<GameUpdateRequest> req) {
        gameService.updateGameResult(req);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
    }
}
