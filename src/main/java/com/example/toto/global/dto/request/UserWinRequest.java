package com.example.toto.global.dto.request;

public record UserWinRequest(
        String type,
        Integer leftMonths,
        Integer amount
) {
    public static UserWinRequest from(Integer amount){
        return new UserWinRequest(
                "토토",
                0,
                amount
        );
    }
}
