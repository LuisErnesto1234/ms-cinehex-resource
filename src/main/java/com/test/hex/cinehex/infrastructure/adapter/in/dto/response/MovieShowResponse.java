package com.test.hex.cinehex.infrastructure.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MovieShowResponse(
    UUID id,
    MovieResponse movieResponse,
    RoomResponse roomResponse,
    Instant starTime,
    Instant endTime,
    BigDecimal price) {}
