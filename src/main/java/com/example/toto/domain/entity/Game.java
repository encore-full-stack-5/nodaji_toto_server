package com.example.toto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
@Table(name = "GAMES")
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ID")
    private Long gameId;

    @Column(name = "GAME_START_AT")
    private Date gameStartAt;

    @Column(name = "GAME_BET_END_AT")
    private Date betEndAt;

    @ManyToOne
    @JoinColumn(name = "TEAM_HOME")
    private Team teamHome;

    @ManyToOne
    @JoinColumn(name = "TEAM_AWAY")
    private Team teamAway;

    @Column(name = "GAME_RTP_HOME")
    private Integer rtpHome;

    @Column(name = "GAME_RTP_AWAY")
    private Integer rtpAway;

    @Column(name = "GAME_RESULT")
    private Integer gameResult;
}
