package com.test.hex.cinehex.infrastructure.adapter.in.dto.events;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserRegisteredEvent(
        UUID userId,
        String email
) {}
