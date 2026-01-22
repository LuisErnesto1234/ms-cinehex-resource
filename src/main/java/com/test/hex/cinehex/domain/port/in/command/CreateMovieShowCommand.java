package com.test.hex.cinehex.domain.port.in.command;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateMovieShowCommand(
    UUID movieId, UUID roomId, Instant startTime, BigDecimal price) {}
