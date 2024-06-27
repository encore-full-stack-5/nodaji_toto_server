package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingGameRequest;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;

import java.util.List;

public interface BettingGameService {
    List<BettingGame> findAllByGameId(Long gameId);
    List<BettingGame> findAllByBettingId(Long bettingId);
    void saveAllByBetting(List<BettingGameRequest> req, Betting betting);
}
