package com.example.toto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
@Table(name = "BETTINGS")
public class Betting {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BETTING_ID")
    private Long bettingId;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "GAME_ID")
    private Long gameId;

    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "BETTING_POINT_AMOUNT")
    private Integer pointAmount;

    @Column(name = "BETTING_CREATED_AT")
    private Date createdAt;

    @Column(name = "BETTING_RESULT")
    private Integer bettingResult;
}
