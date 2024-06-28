package com.example.toto.global.api;

import com.example.toto.global.dto.request.UserPaymentRequest;
import com.example.toto.global.dto.request.UserWinRequest;
import com.example.toto.global.dto.response.UserPaymentResponse;
import com.example.toto.global.dto.response.UserPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApiPayment {
    private final FeignPayment feignPayment;

    public UserPointResponse getPointByUserId(UUID userId) {
        return feignPayment.getPointByUserId(userId);
    }

    public UserPaymentResponse payTotoByUser(UUID userId, Integer amount) {
        return feignPayment.payTotoByUser(userId, UserPaymentRequest.from(amount));
    }

    public void sendWinUser(UUID userId, Integer amount) {
        feignPayment.sendWinUser(userId, UserWinRequest.from(amount));
    }
}
