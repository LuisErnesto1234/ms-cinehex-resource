package com.test.hex.cinehex.domain.port.in.command;

import java.util.List;
import java.util.UUID;

public record CreateBookingCommand(UUID userId,
                                   UUID movieShowId,
                                   List<UUID> seatsIds) {}
