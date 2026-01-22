package com.test.hex.cinehex.domain.port.in.command;

import java.util.UUID;

public record ConfirmBookingCommand(UUID bookingId, String paymentToken) {}
