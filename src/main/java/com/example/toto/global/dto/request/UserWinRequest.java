package com.example.toto.global.dto.request;

public record UserWinRequest(
        Integer amount
) {
    public static UserWinRequest from(Integer amount){
        return new UserWinRequest(
                amount
        );
    }
}
