package com.example.toto.service;

import com.example.toto.TestGameInit;
import com.example.toto.domain.dto.request.BettingGameRequest;
import com.example.toto.domain.dto.request.BettingRequest;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.repository.BettingGameRepository;
import com.example.toto.domain.repository.BettingRepository;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.exception.NotFoundException;
import com.example.toto.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class BettingServiceImplTest {
    @Mock
    private BettingRepository bettingRepository;
    @Mock
    private BettingGameRepository bettingGameRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private BettingServiceImpl bettingService;

    protected final TestGameInit testGameInit;
    BettingServiceImplTest() {
        this.testGameInit = new TestGameInit();
    }

    @Nested
    class findBettingsByUserId {
        @Test
        void 성공_조회됨() {
            // give
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 1000);
            BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());

            List<BettingGame> bettingGames = new ArrayList<>(List.of(
                    new BettingGame(1L, null, testGameInit.game1, 1L, 0),
                    new BettingGame(2L, null, testGameInit.game2, 3L, 0)
            ));
            List<Betting> bettings = new ArrayList<>(List.of(
                    new Betting(1L, userId, 10000, LocalDateTime.now(), bettingGames)
            ));
            BDDMockito.given(bettingRepository.findByUserId(userId)).willReturn(bettings);


            // when
            List<BettingResponse> response = bettingService.findBettingsByUserId(userIdToken);


            //then
            Mockito.verify(bettingRepository, Mockito.times(1)).findByUserId(userId);
            assertEquals(10000, response.get(0).pointAmount());
            assertEquals(1L, response.get(0).bettingGames().get(0).teamId());
            assertEquals(3L, response.get(0).bettingGames().get(1).teamId());
        }

        @Test
        void 성공_배팅_없음() {
                // give
                UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
                String userIdToken = testGameInit.generateToken(userId, 1000);
                BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());
                BDDMockito.given(bettingRepository.findByUserId(userId)).willReturn(new ArrayList<>());


                // when
                List<BettingResponse> response = bettingService.findBettingsByUserId(userIdToken);


                //then
                Mockito.verify(bettingRepository, Mockito.times(1)).findByUserId(userId);
                assertTrue(response.isEmpty());
            }


        @Test
        void 실패_토큰_만료(){
            // give
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 0);
            BDDMockito.given(jwtUtils.parseToken(userIdToken)).willThrow(ExpiredJwtException.class);


            // when
            assertThrows(ExpiredJwtException.class, () -> bettingService.findBettingsByUserId(userIdToken));


            //then
            Mockito.verify(bettingRepository, Mockito.times(0)).findByUserId(userId);
        }
    }

    @Nested
    class insertBetting {
        @Test
        void 성공_추가됨(){
            // give
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 0);
            BettingRequest bettingRequest = new BettingRequest(10000,
                    List.of(new BettingGameRequest(1L, 1L)));
            BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());
            BDDMockito.given(bettingRepository.save(any())).willReturn(null);
            BDDMockito.given(bettingGameRepository.saveAll(any())).willReturn(null);
            BDDMockito.given(gameRepository.findById(1L)).willReturn(Optional.of(testGameInit.game1));

            // when
            bettingService.insertBetting(userIdToken, bettingRequest);

            //then
            Mockito.verify(bettingRepository, Mockito.times(1)).save(any());
            Mockito.verify(bettingGameRepository, Mockito.times(1)).saveAll(any());
        }

        @Test
        void 실패_토큰_만료(){
            // give
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 0);
            BettingRequest bettingRequest = new BettingRequest(10000,
                    List.of(new BettingGameRequest(1L, 1L)));
            BDDMockito.given(jwtUtils.parseToken(userIdToken)).willThrow(ExpiredJwtException.class);

            // when
            assertThrows(ExpiredJwtException.class, () -> bettingService.insertBetting(userIdToken, bettingRequest));

            //then
            Mockito.verify(bettingRepository, Mockito.times(0)).save(any());
            Mockito.verify(bettingGameRepository, Mockito.times(0)).saveAll(any());
        }

        @Test
        void 실패_게임_없음(){
            // give
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            String userIdToken = testGameInit.generateToken(userId, 0);
            BettingRequest bettingRequest = new BettingRequest(10000,
                    List.of(new BettingGameRequest(999L, 1L)));
            BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());
            BDDMockito.given(bettingRepository.save(any())).willReturn(null);

            // when
            assertThrows(NotFoundException.class, () -> bettingService.insertBetting(userIdToken, bettingRequest));

            //then
            Mockito.verify(bettingRepository, Mockito.times(1)).save(any());
            Mockito.verify(bettingGameRepository, Mockito.times(0)).saveAll(any());
        }
    }

    @Nested
    class deleteBetting {
        @Test
        void 성공(){
            // give
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            List<BettingGame> bettingGames = new ArrayList<>(List.of(
                    new BettingGame(1L, null, testGameInit.game1, 1L, 0),
                    new BettingGame(2L, null, testGameInit.game2, 3L, 0)
            ));
            Betting bettings = new Betting(1L, userId, 10000, LocalDateTime.now(), bettingGames);
            BDDMockito.given(bettingRepository.findById(1L)).willReturn(Optional.of(bettings));
            doNothing().when(bettingRepository).deleteById(any());

            // when
            bettingService.deleteBetting(1L);

            // then
            Mockito.verify(bettingRepository, Mockito.times(1)).findById(1L);
            Mockito.verify(bettingRepository, Mockito.times(1)).deleteById(any());
        }

        @Test
        void 실패_배팅없음(){
            // give
            BDDMockito.given(bettingRepository.findById(1L)).willThrow(NotFoundException.class);

            // when
            assertThrows(NotFoundException.class, () -> bettingService.deleteBetting(1L));

            // then
            Mockito.verify(bettingRepository, Mockito.times(1)).findById(1L);
            Mockito.verify(bettingRepository, Mockito.times(0)).deleteById(any());
        }
    }
}