package com.example.toto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

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
