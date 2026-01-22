package com.test.hex.cinehex.infrastructure.adapter.in.transaction;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.port.in.CreateRoomUseCase;

import com.test.hex.cinehex.domain.port.in.command.CreateRoomCommand;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Primary
@RequiredArgsConstructor
public class TransactionalCreateRoomUseCase implements CreateRoomUseCase {

  private final CreateRoomUseCase createRoomUseCase;

  @Transactional(rollbackFor = Exception.class, timeout = 5, propagation = Propagation.REQUIRED)
  @Override
  public Room createRoom(CreateRoomCommand command) {
    return createRoomUseCase.createRoom(command);
  }
}
