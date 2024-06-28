package com.example.toto.global.dto.request;

import java.util.UUID;

public record UserWinRequest(
        UUID userId,
        Integer amount
) {
    public static UserWinRequest from(UUID userId, Integer amount){
        return new UserWinRequest(
                userId,
                amount
        );
    }
}
