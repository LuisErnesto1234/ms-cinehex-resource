package com.test.hex.cinehex.domain.model.details;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.model.Room;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record MovieShowDetails(
    UUID id, Movie movie, Room room, Instant startTime, Instant endTime, BigDecimal price) {}
