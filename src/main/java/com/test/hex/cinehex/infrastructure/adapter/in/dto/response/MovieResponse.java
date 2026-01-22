package com.test.hex.cinehex.infrastructure.adapter.in.dto.response;

import java.util.UUID;

public record MovieResponse(UUID id, String title, Integer durationMinutes) {}
