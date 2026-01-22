package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.port.in.command.CreateRoomCommand;

public interface CreateRoomUseCase {
    Room createRoom(CreateRoomCommand command);
}
