package com.example.toto.domain.repository;

import com.example.toto.domain.entity.BettingGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BettingGameRepository
        extends JpaRepository<BettingGame, Long> {

    List<BettingGame> findAllByGameId(Long gameId);
    List<BettingGame> findAllByBettingId(Long bettingID);
}
