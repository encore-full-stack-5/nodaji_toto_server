package com.example.toto.domain.repository;

import com.example.toto.domain.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository
        extends JpaRepository<Game, Long> {

    @Query(value = "SELECT * FROM games " +
            "WHERE " +
            "game_start_at >= DATE(:date) " +
            "AND " +
            "game_start_at <= DATE(:datePlusOne)", nativeQuery = true)
    Page<Game> findAllGamesByDate(
            Pageable pageable,
            @Param("date") LocalDate date,
            @Param("datePlusOne") LocalDate datePlusOne);

    @Query(value = "SELECT * FROM games " +
            "WHERE " +
            "game_start_at >= DATE(:date) " +
            "AND " +
            "game_start_at <= DATE(:datePlusOne) " +
            "AND " +
            "(team_home = :teamId " +
            "OR " +
            "team_away = :teamId)", nativeQuery = true)
    Page<Game> findAllGamesByDateAndTeam(
            Pageable pageable,
            @Param("date") LocalDate date,
            @Param("datePlusOne") LocalDate datePlusOne,
            @Param("teamId")Long teamId);

    List<Game> findAllGamesByGameResultAndGameStartAtBefore(Integer result, LocalDateTime startAt);
}
