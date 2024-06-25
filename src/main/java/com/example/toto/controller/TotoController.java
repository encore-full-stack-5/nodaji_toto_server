package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.service.BettingService;
import com.example.toto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@EnableScheduling
@RestController
@RequestMapping("api/v1/toto")
@RequiredArgsConstructor
public class TotoController {
    @GetMapping("/version")
    public String getVersion() {
        return "v0.1";
    }
}
