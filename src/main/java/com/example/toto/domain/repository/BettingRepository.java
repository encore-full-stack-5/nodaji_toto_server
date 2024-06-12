package com.example.toto.domain.repository;

import com.example.toto.domain.entity.Betting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BettingRepository
        extends JpaRepository<Betting, Long> {

}
