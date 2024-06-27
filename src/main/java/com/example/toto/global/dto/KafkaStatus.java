package com.example.toto.global.dto;

public record KafkaStatus<T>(
        T data,
        String status
) {
}
