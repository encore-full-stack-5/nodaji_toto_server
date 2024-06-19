package com.example.toto.global.api;

import com.example.toto.global.dto.request.UserPaymentRequest;
import com.example.toto.global.dto.request.UserWinRequest;
import com.example.toto.global.dto.response.UserPaymentResponse;
import com.example.toto.global.dto.response.UserPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiPayment {
    private final FeignPayment feignPayment;

    public UserPointResponse getPointByUserId(String userId) {
        return feignPayment.getPointByUserId(userId);
    }

    public UserPaymentResponse payTotoByUser(String userId, UserPaymentRequest req) {
        return feignPayment.payTotoByUser(userId, req);
    }

    public void sendWinUser(String userId, UserWinRequest req) {
        feignPayment.sendWinUser(userId, req);
    }
}
