package com.example.toto.global.dto.request;

public record UserPaymentRequest(
        String type,
        Long amount
) {
}
