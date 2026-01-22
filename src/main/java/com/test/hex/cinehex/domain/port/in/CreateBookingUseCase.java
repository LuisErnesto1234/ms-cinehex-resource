package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.details.BookingReceipt;
import com.test.hex.cinehex.domain.port.in.command.CreateBookingCommand;

public interface CreateBookingUseCase {
    BookingReceipt createBooking(CreateBookingCommand command);
}
