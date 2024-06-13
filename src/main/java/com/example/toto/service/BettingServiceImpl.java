package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.repository.BettingGameRepository;
import com.example.toto.domain.repository.BettingRepository;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BettingServiceImpl implements BettingService{
    private final BettingRepository bettingRepository;
    private final BettingGameRepository bettingGameRepository;
    private final GameRepository gameRepository;
    private final JwtUtils jwtUtils;

    @Override
    public List<BettingResponse> findBettingsByUserId(String userIdToken) {
        UUID userId = UUID.fromString(jwtUtils.parseToken(userIdToken));
        return bettingRepository.findByUserId(userId).stream().map(BettingResponse::from).toList();
    }

    @Override
    public void insertBetting(String userIdToken, BettingRequest req) {
        Betting betting = req.toEntity(UUID.fromString(jwtUtils.parseToken(userIdToken)));
        bettingRepository.save(betting);
        bettingGameRepository.saveAll(req.bettingGames().stream()
                .map(e -> e.toEntity(betting, gameRepository.findById(e.gameId())
                        .orElseThrow(IllegalArgumentException::new)))
                .toList());
    }

    @Override
    public void deleteBetting(Long bettingId) {

    }
}
