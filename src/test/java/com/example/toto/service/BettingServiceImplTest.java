package com.example.toto.service;

import com.example.toto.TestInit;
import com.example.toto.domain.dto.response.BettingResponse;
import com.example.toto.domain.entity.Betting;
import com.example.toto.domain.entity.BettingGame;
import com.example.toto.domain.repository.BettingGameRepository;
import com.example.toto.domain.repository.BettingRepository;
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

@ExtendWith(MockitoExtension.class)
class BettingServiceImplTest {
    @Mock
    private BettingRepository bettingRepository;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private BettingServiceImpl bettingService;

    protected final TestInit testInit;
    BettingServiceImplTest() {
        this.testInit = new TestInit();
    }

    @Nested
    class findBettingsByUserId {
        @Nested
        class 성공{
            @Test
            void 조회됨() {
                // give
                UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
                String userIdToken = testInit.generateToken(userId, 1000);
                BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());

                List<BettingGame> bettingGames = new ArrayList<>(List.of(
                        new BettingGame(1L, null, testInit.game1, 1L, 0),
                        new BettingGame(2L, null, testInit.game2, 3L, 0)
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
            void 배팅_없음() {
                // give
                UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
                String userIdToken = testInit.generateToken(userId, 1000);
                BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());
                BDDMockito.given(bettingRepository.findByUserId(userId)).willReturn(new ArrayList<>());


                // when
                List<BettingResponse> response = bettingService.findBettingsByUserId(userIdToken);


                //then
                Mockito.verify(bettingRepository, Mockito.times(1)).findByUserId(userId);
                assertTrue(response.isEmpty());
            }
        }

        @Nested
        class 실패 {
            @Test
            void 토큰_만료(){
                // give
                UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
                String userIdToken = testInit.generateToken(userId, 1);
                BDDMockito.given(jwtUtils.parseToken(userIdToken)).willThrow(ExpiredJwtException.class);


                // when
                assertThrows(ExpiredJwtException.class, () -> bettingService.findBettingsByUserId(userIdToken));


                //then
                Mockito.verify(bettingRepository, Mockito.times(0)).findByUserId(userId);
            }

            @Test
            void 유저_없음() {
                // give
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
                String userIdToken = testInit.generateToken(userId, 1000);
                BDDMockito.given(jwtUtils.parseToken(userIdToken)).willReturn(userId.toString());
                BDDMockito.given(bettingRepository.findByUserId(userId)).willThrow(NotFoundException.class);


                // when
                assertThrows(NotFoundException.class, () -> bettingService.findBettingsByUserId(userIdToken));


                //then
                Mockito.verify(bettingRepository, Mockito.times(1)).findByUserId(userId);
            }
        }
    }

    @Nested
    class insertBetting {
        @Test
        void 성공(){

        }

        @Nested
        class 실패 {
            @Test
            void 토큰만료(){

            }

            @Test
            void 유저없음(){

            }

            @Test
            void 게임없음(){

            }
        }
    }

    @Nested
    class deleteBetting {
        @Test
        void 성공(){

        }

        @Test
        void 실패_배팅없음(){

        }
    }

//    @BeforeEach
//    void init() {
//        testInit = new TestInit();
//    }
}