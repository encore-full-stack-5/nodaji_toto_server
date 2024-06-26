package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingGameRequest;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.BettingGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BettingGameServiceImpl implements BettingGameService{
    private final BettingGameRepository bettingGameRepository;

    @Override
    public List<BettingGame> findAllByGameId(Long gameId) {
        return bettingGameRepository.findAllByGame_GameId(gameId);
    }

    @Override
    public List<BettingGame> findAllByBettingId(Long bettingId) {
        return bettingGameRepository.findAllByBettingId_BettingId(bettingId);
    }

    @Override
    public void saveAllByBetting(List<BettingGameRequest> req, Betting betting) {
        bettingGameRepository.saveAll(req.stream()
                .map(e -> e.toEntity(
                        betting,
                        Game.builder().gameId(e.gameId()).build())
                ).toList());
    }
}
