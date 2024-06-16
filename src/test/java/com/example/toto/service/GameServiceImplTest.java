package com.example.toto.service;

import com.example.toto.TestGameInit;
import com.example.toto.TestTeamInit;
import com.example.toto.domain.dto.request.GameRequest;
import com.example.toto.domain.dto.request.GameUpdateRequest;
import com.example.toto.domain.dto.response.GameResponse;
import com.example.toto.domain.entity.Game;
import com.example.toto.domain.repository.GameRepository;
import com.example.toto.domain.repository.TeamRepository;
import com.example.toto.exception.InvalidValueException;
import com.example.toto.exception.NotFoundException;
import com.example.toto.utils.ResultValidationUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private ResultValidationUtils resultValidationUtils;
    @InjectMocks
    private GameServiceImpl gameService;

    protected final TestTeamInit testTeamInit;
    protected final TestGameInit testGameInit;
    GameServiceImplTest() {
        this.testTeamInit = new TestTeamInit();
        this.testGameInit = new TestGameInit();
    }

    @Nested
    class getGamesByParam {
        @Test
        void 성공_파라미터_모두_있음() {
            // give
            LocalDate date = LocalDate.of(2024, 6, 15);
            Long team = 1L;
            List<Game> games = new ArrayList<>(List.of(
                    testGameInit.game1
            ));
            BDDMockito.given(gameRepository.findGamesByDateAndTeam(date, date.plusDays(1), team)).willReturn(games);

            // when
            List<GameResponse> responses = gameService.getGamesByParam(date, team);

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findGamesByDateAndTeam(date, date.plusDays(1), team);
            assertEquals(1L, responses.get(0).teamHomeId());
        }

        @Test
        void 성공_파라미터_모두_없음() {
            // give
            List<Game> games = new ArrayList<>(List.of(
                    new Game(
                            1L,
                            LocalDate.now().atTime(LocalTime.of(18, 30)),
                            LocalDate.now().atTime(LocalTime.of(18, 20)),
                            testTeamInit.teamA,
                            testTeamInit.teamB,
                            1.5f,
                            2f,
                            0
                    ),
                    new Game(
                            2L,
                            LocalDate.now().atTime(LocalTime.of(18, 30)),
                            LocalDate.now().atTime(LocalTime.of(18, 20)),
                            testTeamInit.teamC,
                            testTeamInit.teamD,
                            4f,
                            1.1f,
                            0
                    )
            ));
            BDDMockito.given(gameRepository.findGamesByDate(LocalDate.now(), LocalDate.now().plusDays(1))).willReturn(games);

            // when
            List<GameResponse> responses = gameService.getGamesByParam(null, null);

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findGamesByDate(LocalDate.now(), LocalDate.now().plusDays(1));
            assertEquals(2, responses.size());
        }

        @Test
        void 성공_파라미터_팀_없음() {
            // give
            LocalDate date = LocalDate.of(2024, 6, 15);
            List<Game> games = new ArrayList<>(List.of(
                    testGameInit.game1
            ));
            BDDMockito.given(gameRepository.findGamesByDate(date, date.plusDays(1))).willReturn(games);

            // when
            List<GameResponse> responses = gameService.getGamesByParam(date, null);

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findGamesByDate(date, date.plusDays(1));
            assertEquals(1L, responses.get(0).teamHomeId());
        }

        @Test
        void 성공_파라미터_날짜_없음() {
            // give
            Long team = 1L;
            List<Game> games = new ArrayList<>(List.of(
                    new Game(
                            1L,
                            LocalDate.now().atTime(LocalTime.of(18, 30)),
                            LocalDate.now().atTime(LocalTime.of(18, 20)),
                            testTeamInit.teamA,
                            testTeamInit.teamB,
                            1.5f,
                            2f,
                            0
                    )
            ));
            BDDMockito.given(gameRepository.findGamesByDateAndTeam(LocalDate.now(), LocalDate.now().plusDays(1), team)).willReturn(games);

            // when
            List<GameResponse> responses = gameService.getGamesByParam(LocalDate.now(), team);

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findGamesByDateAndTeam(LocalDate.now(), LocalDate.now().plusDays(1), team);
            assertEquals(1L, responses.get(0).teamHomeId());
        }
    }

    @Nested
    class insertGame{
        @Test
        void 성공_추가됨() {
            // give
            List<GameRequest> games = new ArrayList<>(List.of(
                    new GameRequest(
                            LocalDateTime.of(2024, 6, 15, 18, 30),
                            LocalDateTime.of(2024, 6, 15, 18, 20),
                            1L,
                            2L,
                            1.5f,
                            2f
                    )
            ));
            BDDMockito.given(teamRepository.findById(1L)).willReturn(Optional.of(testTeamInit.teamA));
            BDDMockito.given(teamRepository.findById(2L)).willReturn(Optional.of(testTeamInit.teamB));
            BDDMockito.given(gameRepository.saveAll(any())).willReturn(null);

            // when
            gameService.insertGame(games);

            // then
            Mockito.verify(teamRepository, Mockito.times(2)).findById(any());
            Mockito.verify(gameRepository, Mockito.times(1)).saveAll(any());
        }

        @Test
        void 실패_팀_없음() {
            // give
            List<GameRequest> games = new ArrayList<>(List.of(
                    new GameRequest(
                            LocalDateTime.of(2024, 6, 15, 18, 30),
                            LocalDateTime.of(2024, 6, 15, 18, 20),
                            999L,
                            9999L,
                            1.5f,
                            2f
                    )
            ));
            BDDMockito.given(teamRepository.findById(999L)).willThrow(NotFoundException.class);

            // when
            assertThrows(NotFoundException.class, () -> gameService.insertGame(games));

            // then
            Mockito.verify(teamRepository, Mockito.times(1)).findById(any());
            Mockito.verify(gameRepository, Mockito.times(0)).saveAll(any());
        }
    }

    @Nested
    class updateGameResult {
        @Test
        void 성공_갱신됨() {
            // give
            List<GameUpdateRequest> gameUpdateRequests = new ArrayList<>(List.of(
                    new GameUpdateRequest(1L, 1)
            ));
            BDDMockito.given(gameRepository.findById(1L)).willReturn(Optional.of(testGameInit.game1));
            BDDMockito.given(resultValidationUtils.gameResultValidation(1)).willReturn("HomeWin");

            // when
            gameService.updateGameResult(gameUpdateRequests);

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findById(1L);
        }

        @Test
        void 실패_게임_없음() {
            // give
            List<GameUpdateRequest> gameUpdateRequests = new ArrayList<>(List.of(
                    new GameUpdateRequest(999L, 1)
            ));
            BDDMockito.given(gameRepository.findById(999L)).willThrow(NotFoundException.class);

            // when
            assertThrows(NotFoundException.class, () -> gameService.updateGameResult(gameUpdateRequests));

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findById(999L);
        }

        @Test
        void 실패_값_검증_실패() {
            // give
            List<GameUpdateRequest> gameUpdateRequests = new ArrayList<>(List.of(
                    new GameUpdateRequest(1L, 999)
            ));
            BDDMockito.given(gameRepository.findById(1L)).willReturn(Optional.of(testGameInit.game1));
            BDDMockito.given(resultValidationUtils.gameResultValidation(999)).willThrow(InvalidValueException.class);

            // when
            assertThrows(InvalidValueException.class, () -> gameService.updateGameResult(gameUpdateRequests));

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findById(1L);
        }
    }

    @Nested
    class deleteGame{
        @Test
        void 성공_삭제됨() {
            // give
            BDDMockito.given(gameRepository.findById(1L)).willReturn(Optional.of(testGameInit.game1));
            doNothing().when(gameRepository).delete(any());

            // when
            gameService.deleteGame(1L);

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findById(1L);
            Mockito.verify(gameRepository, Mockito.times(1)).delete(any());
        }

        @Test
        void 실패_게임_없음() {
            // give
            BDDMockito.given(gameRepository.findById(1L)).willThrow(NotFoundException.class);

            // when
            assertThrows(NotFoundException.class, () -> gameService.deleteGame(1L));

            // then
            Mockito.verify(gameRepository, Mockito.times(1)).findById(1L);
            Mockito.verify(gameRepository, Mockito.times(0)).delete(any());
        }
    }
}