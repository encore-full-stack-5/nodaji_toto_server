package com.example.toto.domain.repository;

import com.example.toto.domain.entity.Betting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BettingRepository
        extends JpaRepository<Betting, Long> {

    List<Betting> findAllByUserId(UUID userId);
}
