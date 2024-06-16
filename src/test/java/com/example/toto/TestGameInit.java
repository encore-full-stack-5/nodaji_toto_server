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
    public final Game game1;
    public final Game game2;
    private final SecretKey secretKey;

    public final String generateToken(UUID uuid, Integer expiration) {
        return Jwts.builder()
                .subject(uuid.toString())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public TestGameInit() {
        TestTeamInit testTeamInit = new TestTeamInit();
        secretKey = Keys.hmacShaKeyFor("SecretKeyHereSecretKeyHereSecretKeyHereSecretKeyHere".getBytes());
        game1 = new Game(
            1L,
            LocalDateTime.of(2024,6,15,18,30),
            LocalDateTime.of(2024,6,15,18,20),
            testTeamInit.teamA,
            testTeamInit.teamB,
            1.5f,
            2f,
            0
        );
        game2 = new Game(
                2L,
                LocalDateTime.of(2024,6,15,18,30),
                LocalDateTime.of(2024,6,15,18,20),
                testTeamInit.teamC,
                testTeamInit.teamD,
                1.1f,
                3f,
                0
        );
    }
}
