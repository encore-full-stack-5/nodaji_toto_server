package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/toto/games")
@RequiredArgsConstructor
public class GameController {
    private GameService gameService;

    @GetMapping
    public List<GameResponse> getGames(
            @RequestParam(name = "date", required = false) Date date,
            @RequestParam(name = "team", required = false) String team
    ) {
        return null;
    }

    @PostMapping
    public void addGame(@RequestBody GameRequest req) {

    }

    @PutMapping
    public void updateGameResult(@RequestBody List<GameUpdateRequest> req) {

    }

    @DeleteMapping("/{id}")
    public void deleteGame(@RequestParam("id") Long id) {

    }
}
