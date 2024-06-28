package com.example.toto.global.api;

import com.example.toto.global.dto.request.UserPaymentRequest;
import com.example.toto.global.dto.request.UserWinRequest;
import com.example.toto.global.dto.response.UserPaymentResponse;
import com.example.toto.global.dto.response.UserPointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "PaymentApi", url = "http://192.168.0.10:8084")
public interface FeignPayment {

    @GetMapping("/api/v1/accounts/{userId}")
    UserPointResponse getPointByUserId(@PathVariable("userId") UUID userId);

    @PutMapping("/api/v1/payments/{userId}")
    UserPaymentResponse payTotoByUser(
            @PathVariable("userId") UUID userId,
            @RequestBody UserPaymentRequest req
    );

    @PostMapping("/api/v1/toto")
    void sendWinUser(@RequestBody UserWinRequest req);
}
