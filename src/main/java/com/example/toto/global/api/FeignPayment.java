package com.example.toto.global.api;

import com.example.toto.global.dto.request.UserPaymentRequest;
import com.example.toto.global.dto.request.UserWinRequest;
import com.example.toto.global.dto.response.UserPaymentResponse;
import com.example.toto.global.dto.response.UserPointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "PaymentApi", url = "http://192.168.0.10:8080")
public interface FeignPayment {

    @GetMapping("/api/v1/accounts/{userId}")
    UserPointResponse getPointByUserId(@PathVariable("userId") String userId);

    @PostMapping("/api/v1/payments/{userId}")
    UserPaymentResponse payTotoByUser(
            @PathVariable("userId") UUID userId,
            @RequestBody UserPaymentRequest req
    );

    @PostMapping("/api/v1/win/{userId}")
    void sendWinUser(
            @PathVariable("userId") UUID userId,
            @RequestBody UserWinRequest req
    );
}
