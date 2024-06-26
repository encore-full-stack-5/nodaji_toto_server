package com.example.toto.controller;

import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.service.BettingService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/toto/betting")
@RequiredArgsConstructor
public class BettingController {
    private final BettingService bettingService;

    @GetMapping
    public List<BettingResponse> getAllBettingsByUserId(
            @RequestHeader("Authorization") String userIdToken
    ) {
        return bettingService.findBettingsByUserId(userIdToken);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBetting(
            @RequestHeader("Authorization") String userIdToken,
            @RequestBody BettingRequest req
    ) {
        bettingService.insertBetting(userIdToken, req);
    }

    @DeleteMapping("/{bettingId}")
    public void deleteBetting(
            @PathVariable("bettingId") Long bettingId
    ) {
        bettingService.deleteBetting(bettingId);
    }
}
