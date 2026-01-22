package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.details.BookingReceipt;
import com.test.hex.cinehex.domain.port.in.command.ConfirmBookingCommand;

public interface ConfirmBookingUseCase {

    BookingReceipt confirmBooking(ConfirmBookingCommand command);

}
