package com.example.toto.controller;

import com.example.toto.service.TotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toto")
@RequiredArgsConstructor
public class TotoController {
    private TotoService totoService;
}
