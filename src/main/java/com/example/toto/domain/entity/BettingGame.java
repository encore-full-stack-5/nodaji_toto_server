package com.example.toto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
@Table(name = "BETTING_GAME")
public class BettingGame {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BETTING_GAME_ID")
    private Long bettingGameId;

    @ManyToOne
    @JoinColumn(name = "BETTING_ID", nullable = false)
    private Betting bettingId;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game gameId;

    @Column(name = "TEAM_ID", nullable = false)
    private Long teamId;

    @Column(name = "BETTING_GAME_RESULT")
    private Integer result;
}
