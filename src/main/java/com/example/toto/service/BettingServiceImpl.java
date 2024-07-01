package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.repository.BettingRepository;
import com.example.toto.exception.NotFoundException;
import com.example.toto.global.api.ApiPayment;
import com.example.toto.global.dto.request.EmailRequest;
import com.example.toto.global.kafka.MailProducer;
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
    private final BettingGameService bettingGameService;
    private final ApiPayment apiPayment;
    private final MailProducer mailProducer;
    private final JwtUtils jwtUtils;

    @Override
    public List<BettingResponse> findBettingsByUserId(String userIdToken) {
        UUID userId = UUID.fromString(jwtUtils.parseToken(userIdToken));
        return bettingRepository.findAllByUserId(userId).stream().map(BettingResponse::from).toList();
    }

    @Override
    @Transactional
    public void insertBetting(String userIdToken, BettingRequest req) {
        UUID uuid = UUID.fromString(jwtUtils.parseToken(userIdToken));
//        String status = apiPayment.payTotoByUser(uuid, req.pointAmount()).status();
//        System.out.println(status);
        Betting betting = req.toEntity(uuid);
        bettingRepository.save(betting);
        bettingGameService.saveAllByBetting(req.bettingGames(), betting);
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
        List<BettingGame> allBettingGamesByGame = bettingGameService.findAllByGameId(req.gameId());
        allBettingGamesByGame.forEach(bettingGame -> {
            bettingGame.setBettingResult(req.result());
            List<BettingGame> allBettingGamesByBetting = bettingGameService.findAllByBettingId(bettingGame.getBettingId().getBettingId());
            int filterResult = allBettingGamesByBetting.stream().filter(e -> e.getTeam().equals(e.getResult()) || e.getResult() > 2).toList().size();
            if(allBettingGamesByBetting.size() == filterResult) {
                float rtp = 1f;
                for(BettingGame e : allBettingGamesByBetting) {
                    if (e.getResult() == 1) rtp *= e.getGame().getRtpHome();
                    else if(e.getResult() == 2) rtp *= e.getGame().getRtpAway();
                }
                //메일발송 및 상금 수령
                UUID userId = bettingGame.getBettingId().getUserId();
                apiPayment.sendWinUser(userId, (int) Math.floor(bettingGame.getBettingId().getPointAmount() * rtp));
                mailProducer.send(EmailRequest.byUserId(userId));
            }
        });
    }
}
