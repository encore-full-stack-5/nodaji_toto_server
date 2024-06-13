package com.example.toto.domain.repository;

import com.example.toto.domain.entity.BettingGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BettingGameRepository
        extends JpaRepository<BettingGame, Long> {
}
