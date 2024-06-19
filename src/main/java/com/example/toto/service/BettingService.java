package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.BettingResponse;

import java.util.List;

public interface BettingService {
    List<BettingResponse> findBettingsByUserId(String userIdToken);
    void insertBetting(String userIdToken, BettingRequest req);
    void deleteBetting(Long bettingId);
    void updateBettingResult(GameUpdateRequest req);
}
