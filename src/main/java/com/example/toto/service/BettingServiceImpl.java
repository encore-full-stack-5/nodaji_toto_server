package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.repository.BettingGameRepository;
import com.example.toto.domain.repository.BettingRepository;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.exception.NotFoundException;
import com.example.toto.utils.JwtUtils;
import jakarta.transaction.Transactional;
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
        return bettingRepository.findAllByUserId(userId).stream().map(BettingResponse::from).toList();
    }

    @Override
    @Transactional
    public void insertBetting(String userIdToken, BettingRequest req) {
        Betting betting = req.toEntity(UUID.fromString(jwtUtils.parseToken(userIdToken)));
        bettingRepository.save(betting);
        bettingGameRepository.saveAll(req.bettingGames().stream()
                .map(e -> e.toEntity(betting, gameRepository.findById(e.gameId())
                        .orElseThrow(() -> new NotFoundException("GAME"))))
                .toList());
    }

    @Override
    @Transactional
    public void deleteBetting(Long bettingId) {
        bettingRepository.findById(bettingId).orElseThrow(() -> new NotFoundException("BETTING"));
        bettingRepository.deleteById(bettingId);
    }

    @Override
    @Transactional
    public void updateBettingResult(GameUpdateRequest req) {
        List<BettingGame> byGameId = bettingGameRepository.findAllByGame_GameId(req.gameId());
        byGameId.forEach(betting -> {
            betting.setBettingResult(betting.getTeamId().equals(req.gameId()) ? 2 : 1);
            List<BettingGame> allByBettingId = bettingGameRepository.findAllByBettingId_BettingId(betting.getBettingId().getBettingId());
            List<BettingGame> filterByResult = allByBettingId.stream().filter(
                            bettingGame -> bettingGame.getResult().equals(2)).toList();
            if(allByBettingId.size() == filterByResult.size()) {
                Betting targetBetting = bettingRepository.findById(betting.getBettingId().getBettingId()).orElseThrow();
                UUID userId = targetBetting.getUserId();
                // 메일발송
                // 정산
            }
        });
    }
}
