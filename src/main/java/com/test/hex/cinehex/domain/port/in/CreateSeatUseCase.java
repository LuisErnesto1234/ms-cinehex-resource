package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.details.SeatDetails;
import com.test.hex.cinehex.domain.port.in.command.CreateSeatCommand;

public interface CreateSeatUseCase {
    SeatDetails createSeat(CreateSeatCommand command);
}
