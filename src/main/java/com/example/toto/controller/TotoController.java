package com.example.toto.controller;

import com.example.toto.service.TotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/toto")
@RequiredArgsConstructor
public class TotoController {
    private TotoService totoService;
}
