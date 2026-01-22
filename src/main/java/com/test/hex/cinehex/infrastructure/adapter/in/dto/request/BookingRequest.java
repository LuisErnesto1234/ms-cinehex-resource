package com.test.hex.cinehex.infrastructure.adapter.in.dto.request;

import java.util.List;
import java.util.UUID;

public record BookingRequest(UUID userId, UUID movieShowId, List<UUID> seatsIds) {}
