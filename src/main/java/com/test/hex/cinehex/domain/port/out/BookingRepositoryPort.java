package com.test.hex.cinehex.domain.port.out;

import com.test.hex.cinehex.domain.model.Booking;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepositoryPort {
    Booking save(Booking booking);

    boolean existsById(UUID id);

    List<UUID> findOccupiedSeats(UUID movieShowId, List<UUID> seatsIds);

    int expireOldReservations(Instant limit);

    Optional<Booking> findById(UUID bookingId);
}
