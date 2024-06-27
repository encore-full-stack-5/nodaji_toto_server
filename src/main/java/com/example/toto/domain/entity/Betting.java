package com.example.toto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "BETTING_POINT_AMOUNT", nullable = false)
    private Integer pointAmount;

    @CreationTimestamp
    @Column(name = "BETTING_CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "bettingId", cascade = CascadeType.REMOVE)
    private List<BettingGame> bettingGames;
}
