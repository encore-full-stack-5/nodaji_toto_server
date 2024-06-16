package com.example.toto.controller;

import com.example.toto.domain.repository.GameRepository;
import com.example.toto.service.GameServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class getGames {
        @Test
        void 성공_파라미터_모두_있음() {

        }

        @Test
        void 성공_파라미터_날짜_없음() {

        }

        @Test
        void 성공_파라미터_팀_없음() {

        }

        @Test
        void 성공_파라미터_모두_없음() {

        }
    }

    @Nested
    class addGame {
        @Test
        void 성공_추가됨() {

        }

        @Test
        void 실패_팀_없음() {

        }
    }

    @Nested
    class updateGameResult {
        @Test
        void 성공_갱신됨() {

        }

        @Test
        void 실패_게임_없음() {

        }

        @Test
        void 실패_값_검증_실패() {

        }
    }

    @Nested
    class deleteGame {
        @Test
        void 성공_삭제됨() {

        }

        @Test
        void 실패_게임_없음() {

        }
    }
}