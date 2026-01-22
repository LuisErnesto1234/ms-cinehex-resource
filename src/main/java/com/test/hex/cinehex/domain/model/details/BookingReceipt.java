package com.test.hex.cinehex.domain.model.details;

import com.test.hex.cinehex.domain.model.Booking;
import com.test.hex.cinehex.domain.model.MovieShow;
import com.test.hex.cinehex.domain.model.Seat;
import com.test.hex.cinehex.domain.model.User;

import lombok.Builder;

import java.util.List;

@Builder
public record BookingReceipt(
        Booking booking,
        User user,
        MovieShow movieShow,
        List<Seat> seats
) {}