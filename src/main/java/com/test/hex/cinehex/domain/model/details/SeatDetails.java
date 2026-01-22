package com.test.hex.cinehex.domain.model.details;

import com.test.hex.cinehex.domain.model.Room;

import java.util.UUID;

public record SeatDetails(UUID id, String row,
                          Integer number, Room room) {}
