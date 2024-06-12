package com.example.toto.service;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.domain.repository.BettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BettingServiceImpl implements BettingService{
    private final BettingRepository bettingRepository;

    @Override
    public List<BettingResponse> findBettingsByUserId(String userIdToken) {
        return List.of();
    }

    @Override
    public void insertBetting(BettingRequest req) {

    }

    @Override
    public void deleteBetting(Long bettingId) {

    }
}
