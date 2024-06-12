package com.example.toto.controller;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.service.BettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bet")
@RequiredArgsConstructor
public class BettingController {
    private final BettingService bettingService;

    @GetMapping("/{userIdToken}")
    public List<BettingResponse> getAllBettingsByUserId(@PathVariable String userIdToken) {
        return bettingService.findBettingsByUserId(userIdToken);
    }

    @PostMapping("/{userIdToken}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBetting(
            @PathVariable String userIdToken,
            @RequestBody BettingRequest req) {
        bettingService.insertBetting(userIdToken, req);
    }

    @DeleteMapping("/{bettingId}")
    public void deleteBetting(@PathVariable Long bettingId) {
        bettingService.deleteBetting(bettingId);
    }
}
