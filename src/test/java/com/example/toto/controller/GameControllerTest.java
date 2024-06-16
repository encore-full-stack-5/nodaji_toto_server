package com.example.toto.controller;

import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.exception.InvalidValueException;
import com.example.toto.exception.NotFoundException;
import com.example.toto.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {
    @MockBean
    private GameService gameService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class getGames {
        @Test
        void 성공_파라미터_모두_있음() throws Exception {
            LocalDate date = LocalDate.of(2024, 6, 15);
            Long team = 1L;
            BDDMockito.given(gameService.getGamesByParam(date, team)).willReturn(
                    List.of(
                            new GameResponse(
                                    1L,
                                    LocalDateTime.of(2024,6,15,18,30),
                                    LocalDateTime.of(2024,6,15,18,20),
                                    1L,
                                    2L,
                                    1.5f,
                                    2f,
                                    0
                            )
                    )
            );

            mvc.perform(get("/api/v1/toto/games?date=" + date + "&team=" + team))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].gameId").value(1))
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void 성공_파라미터_날짜_없음() throws Exception {
            Long team = 1L;
            BDDMockito.given(gameService.getGamesByParam(LocalDate.now(), team)).willReturn(
                    List.of(
                            new GameResponse(
                                    1L,
                                    LocalDateTime.of(2024,6,15,18,30),
                                    LocalDateTime.of(2024,6,15,18,20),
                                    1L,
                                    2L,
                                    1.5f,
                                    2f,
                                    0
                            )
                    )
            );

            mvc.perform(get("/api/v1/toto/games?team=" + team))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].gameId").value(1))
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void 성공_파라미터_팀_없음() throws Exception {
            LocalDate date = LocalDate.of(2024, 6, 15);
            Long team = 1L;
            BDDMockito.given(gameService.getGamesByParam(date, null)).willReturn(
                    List.of(
                            new GameResponse(
                                    1L,
                                    LocalDateTime.of(2024,6,15,18,30),
                                    LocalDateTime.of(2024,6,15,18,20),
                                    1L,
                                    2L,
                                    1.5f,
                                    2f,
                                    0
                            )
                    )
            );

            mvc.perform(get("/api/v1/toto/games?date=" + date))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].gameId").value(1))
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void 성공_파라미터_모두_없음() throws Exception {
            BDDMockito.given(gameService.getGamesByParam(LocalDate.now(), null)).willReturn(
                    List.of(
                            new GameResponse(
                                    1L,
                                    LocalDateTime.of(2024,6,15,18,30),
                                    LocalDateTime.of(2024,6,15,18,20),
                                    1L,
                                    2L,
                                    1.5f,
                                    2f,
                                    0
                            )
                    )
            );

            mvc.perform(get("/api/v1/toto/games"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].gameId").value(1))
                    .andDo(MockMvcResultHandlers.print());
        }
    }

    @Nested
    class addGame {
        @Test
        void 성공_추가됨() throws Exception{
            String request = objectMapper.writeValueAsString(List.of(
                    new GameRequest(
                            LocalDateTime.of(2024, 6, 15, 18, 30),
                            LocalDateTime.of(2024, 6, 15, 18, 20),
                            1L,
                            2L,
                            1.5f,
                            2f
                    )
            ));

            mvc.perform(post("/api/v1/toto/games")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        void 실패_팀_없음() throws Exception {
            String request = objectMapper.writeValueAsString(List.of(
                    new GameRequest(
                            LocalDateTime.of(2024, 6, 15, 18, 30),
                            LocalDateTime.of(2024, 6, 15, 18, 20),
                            999L,
                            9999L,
                            1.5f,
                            2f
                    )
            ));
            doThrow(NotFoundException.class).when(gameService).insertGame(any());

            mvc.perform(post("/api/v1/toto/games")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect((r) -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(NotFoundException.class)));
        }
    }

    @Nested
    class updateGameResult {
        @Test
        void 성공_갱신됨() throws Exception {
            String request = objectMapper.writeValueAsString(List.of(
                    new GameUpdateRequest(1L, 1)
            ));

            mvc.perform(put("/api/v1/toto/games")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void 실패_게임_없음() throws Exception {
            String request = objectMapper.writeValueAsString(List.of(
                    new GameUpdateRequest(1L, 1)
            ));
            doThrow(NotFoundException.class).when(gameService).updateGameResult(any());

            mvc.perform(put("/api/v1/toto/games")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect((r) -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(NotFoundException.class)));
        }

        @Test
        void 실패_값_검증_실패() throws Exception {
            String request = objectMapper.writeValueAsString(List.of(
                    new GameUpdateRequest(1L, 1)
            ));
            doThrow(InvalidValueException.class).when(gameService).updateGameResult(any());

            mvc.perform(put("/api/v1/toto/games")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect((r) -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(InvalidValueException.class)));
        }
    }

    @Nested
    class deleteGame {
        @Test
        void 성공_삭제됨() throws Exception {
            long gameId = 1L;

            mvc.perform(delete("/api/v1/toto/games/" + gameId))
                    .andExpect(status().isOk());
        }

        @Test
        void 실패_게임_없음() throws Exception {
            long gameId = 1L;
            doThrow(NotFoundException.class).when(gameService).deleteGame(any());

            mvc.perform(delete("/api/v1/toto/games/" + gameId))
                    .andExpect((r) -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(NotFoundException.class)));
        }
    }
}