package com.example.toto;

import com.example.toto.domain.entity.Game;
import com.example.toto.domain.entity.Team;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class TestGameInit {
    public final Team teamA = new Team(1L, "teamA", null, null);
    public final Team teamB = new Team(2L, "teamB", null, null);
    public final Team teamC = new Team(3L, "teamC", null, null);
    public final Team teamD = new Team(4L, "teamD", null, null);
    public final Game game1 = new Game(
            1L,
            LocalDateTime.of(2024,6,15,18,30),
            LocalDateTime.of(2024,6,15,18,20),
            teamA,
            teamB,
            1.5f,
            2f,
            0
    );
    public final Game game2 = new Game(
            2L,
            LocalDateTime.of(2024,6,15,18,30),
            LocalDateTime.of(2024,6,15,18,20),
            teamC,
            teamD,
            1.1f,
            3f,
            0
    );

    private final SecretKey secretKey = Keys.hmacShaKeyFor("SecretKeyHereSecretKeyHereSecretKeyHereSecretKeyHere".getBytes());
    public final String generateToken(UUID uuid, Integer expiration) {
        return Jwts.builder()
                .subject(uuid.toString())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }
}
