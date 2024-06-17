package com.example.toto.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BettingControllerTest {
    @Nested
    class getAllBettingsByUserId {
        @Test
        void 성공_조회됨() {

        }

        @Test
        void 실패_토큰_만료() {

        }
    }

    @Nested
    class addBetting {
        @Test
        void 성공_추가됨() {

        }

        @Test
        void 실패_토큰_만료() {

        }

        @Test
        void 실패_게임_없음() {

        }
    }

    @Nested
    class deleteBetting {
        @Test
        void 성공_추가됨() {

        }

        @Test
        void 실패_배팅_없음() {

        }
    }
}