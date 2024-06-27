package com.example.toto.global.dto.request;

public record UserPaymentRequest(
        String type,
        Integer amount
) {
    public static UserPaymentRequest from(Integer amount) {
        return new UserPaymentRequest("toto", amount);
    }
}
