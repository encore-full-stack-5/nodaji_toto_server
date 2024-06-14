package com.example.toto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
@Table(name = "GAMES")
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ID")
    private Long gameId;

    @Column(name = "GAME_START_AT", nullable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime gameStartAt;

    @Column(name = "GAME_BET_END_AT", nullable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime betEndAt;

    @ManyToOne
    @JoinColumn(name = "TEAM_HOME", nullable = false)
    private Team teamHome;

    @ManyToOne
    @JoinColumn(name = "TEAM_AWAY", nullable = false)
    private Team teamAway;

    @Column(name = "GAME_RTP_HOME", nullable = false)
    private Float rtpHome;

    @Column(name = "GAME_RTP_AWAY", nullable = false)
    private Float rtpAway;

    @Column(name = "GAME_RESULT")
    private Integer gameResult;
}
