package com.test.hex.cinehex.infrastructure.adapter.in.dto.response;

import com.test.hex.cinehex.domain.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record BookingResponse(UUID id,
                              UserResponse userResponse,
                              MovieShowResponse movieShowResponse,
                              List<SeatResponse> seatResponses,
                              BigDecimal totalAmount,
                              BookingStatus bookingStatus,
                              Instant createdAt) {}
