package com.example.toto.global.dto.request;

import java.util.UUID;

public record EmailRequest(
        String id,
        String game
) {
    public static EmailRequest byUserId(UUID userId) {
        return new EmailRequest(
                userId.toString(),
                "toto"
        );
    }
}
