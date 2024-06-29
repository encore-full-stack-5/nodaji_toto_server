package com.example.toto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
@Table(name = "TEAMS")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "TEAM_NAME", nullable = false)
    private String teamName;

    @OneToMany(mappedBy = "teamHome")
    private List<Game> homeGame;

    @OneToMany(mappedBy = "teamAway")
    private List<Game> awayGame;
}
