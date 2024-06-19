package com.example.toto.domain.repository;

import com.example.toto.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository
        extends JpaRepository<Game, Long> {

    @Query(value = "SELECT * FROM GAMES " +
            "WHERE " +
            "GAME_START_AT >= DATE(:date) " +
            "AND " +
            "GAME_START_AT <= DATE(:datePlusOne)", nativeQuery = true)
    List<Game> findAllGamesByDate(
            @Param("date") LocalDate date,
            @Param("datePlusOne") LocalDate datePlusOne);

    @Query(value = "SELECT * FROM GAMES " +
            "WHERE " +
            "GAME_START_AT >= DATE(:date) " +
            "AND " +
            "GAME_START_AT <= DATE(:datePlusOne) " +
            "AND " +
            "(TEAM_HOME = :teamId " +
            "OR " +
            "TEAM_AWAY = :teamId)", nativeQuery = true)
    List<Game> findAllGamesByDateAndTeam(
            @Param("date") LocalDate date,
            @Param("datePlusOne") LocalDate datePlusOne,
            @Param("teamId")Long teamId);

    List<Game> findAllGamesByGameResultAndGameStartAtBefore(Integer result, LocalDateTime startAt);
}
