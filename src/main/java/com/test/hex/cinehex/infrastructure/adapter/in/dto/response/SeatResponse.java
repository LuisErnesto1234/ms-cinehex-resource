package com.test.hex.cinehex.infrastructure.adapter.in.dto.response;

import java.util.UUID;

public record SeatResponse(UUID id, String row, Integer number, RoomResponse roomResponse) {}
