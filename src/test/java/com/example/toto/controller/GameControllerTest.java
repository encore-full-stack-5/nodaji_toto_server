package com.example.toto.controller;

import com.example.toto.domain.repository.GameRepository;
import com.example.toto.service.GameServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(GameController.class)
class GameControllerTest {
    @MockBean
    private GameServiceImpl gameService;
    @Mock
    private GameRepository gameRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getGames() {
    }

    @Test
    void addGame() {
    }

    @Test
    void updateGameResult() {
    }

    @Test
    void deleteGame() {
    }
}