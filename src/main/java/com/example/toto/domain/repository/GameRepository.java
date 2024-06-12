package com.example.toto.domain.repository;

import com.example.toto.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository
        extends JpaRepository<Game, Long> {

}
