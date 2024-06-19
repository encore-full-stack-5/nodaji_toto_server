package com.example.toto.global.dto.request;

public record UserWinRequest(
        String type,
        Integer leftMonths,
        Long amount
) {
}
