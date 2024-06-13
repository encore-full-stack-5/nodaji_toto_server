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
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game gameId;

    @ManyToOne
    @JoinColumn(name = "BETTING_ID", nullable = false)
    private Betting bettingId;
}
