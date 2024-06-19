package com.example.toto.controller;

import com.example.toto.TestGameInit;
import com.example.toto.domain.dto.request.BettingGameRequest;
import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.response.BettingGameResponse;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.exception.NotFoundException;
import com.example.toto.service.BettingService;
import com.example.toto.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BettingController.class)
class BettingControllerTest {
    @MockBean
    private BettingService bettingService;
    @Mock
    private JwtUtils jwtUtils;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    protected final TestGameInit testGameInit;
    BettingControllerTest() {
        this.testGameInit = new TestGameInit();
    }

    @Nested
    class getAllBettingsByUserId {
        @Test
        void 성공_조회됨() throws Exception {
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 1000);
            BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());
            List<BettingGameResponse> bettingGameResponses = new ArrayList<>(List.of(
                    new BettingGameResponse(
                            1L,
                            LocalDateTime.of(2024,6,15,18,30),
                            "teamA",
                            "teamB",
                            1.5f,
                            1L,
                            0
                    )
            ));
            List<BettingResponse> bettingResponses = new ArrayList<>(List.of(
                    new BettingResponse(
                            1L,
                            userId,
                            10000,
                            LocalDateTime.of(2024,6,15,18,30),
                            bettingGameResponses
                    )
            ));
            BDDMockito.given(bettingService.findBettingsByUserId(userIdToken)).willReturn(bettingResponses);

            mvc.perform(get("/api/v1/toto/betting")
                            .header("Authorization", userIdToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].bettingGames[0].gameId").value(1))
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void 실패_토큰_만료() throws Exception {
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 0);
            BDDMockito.given(bettingService.findBettingsByUserId(userIdToken)).willThrow(ExpiredJwtException.class);

            mvc.perform(get("/api/v1/toto/betting")
                            .header("Authorization", userIdToken))
                    .andExpect(r -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(ExpiredJwtException.class)));
        }
    }

    @Nested
    class addBetting {
        @Test
        void 성공_추가됨() throws Exception {
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 1000);
            String request = objectMapper.writeValueAsString(
                    new BettingRequest(
                            10000,
                            List.of(
                                    new BettingGameRequest(1L, 1),
                                    new BettingGameRequest(2L, 2)
                            )
                    )
            );

            mvc.perform(post("/api/v1/toto/betting")
                            .header("Authorization", userIdToken)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        void 실패_토큰_만료() throws Exception {
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 1000);
            BettingRequest bettingRequest = new BettingRequest(
                    10000,
                    List.of(
                            new BettingGameRequest(1L, 1),
                            new BettingGameRequest(2L, 2)
                    )
            );
            String request = objectMapper.writeValueAsString(bettingRequest);
            doThrow(ExpiredJwtException.class).when(bettingService).insertBetting(userIdToken, bettingRequest);

            mvc.perform(post("/api/v1/toto/betting")
                            .header("Authorization", userIdToken)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(r -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(ExpiredJwtException.class)));
        }

        @Test
        void 실패_게임_없음() throws Exception {
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 1000);
            BettingRequest bettingRequest = new BettingRequest(
                    10000,
                    List.of(
                            new BettingGameRequest(1L, 1),
                            new BettingGameRequest(2L, 2)
                    )
            );
            String request = objectMapper.writeValueAsString(bettingRequest);
            doThrow(NotFoundException.class).when(bettingService).insertBetting(userIdToken, bettingRequest);

            mvc.perform(post("/api/v1/toto/betting")
                            .header("Authorization", userIdToken)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(r -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(NotFoundException.class)));
        }
    }

    @Nested
    class deleteBetting {
        @Test
        void 성공_삭제됨() throws Exception {
            long bettingId = 1L;

            mvc.perform(delete("/api/v1/toto/betting/" + bettingId))
                    .andExpect(status().isOk());
        }

        @Test
        void 실패_배팅_없음() throws Exception {
            long bettingId = 1L;
            doThrow(NotFoundException.class).when(bettingService).deleteBetting(any());

            mvc.perform(delete("/api/v1/toto/betting/" + bettingId))
                    .andExpect(r -> assertTrue(
                            r.getResolvedException().getClass()
                                    .isAssignableFrom(NotFoundException.class)));
        }
    }
}