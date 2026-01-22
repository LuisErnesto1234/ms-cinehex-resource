package com.test.hex.cinehex.infrastructure.adapter.in.mapper;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.port.in.command.CreateRoomCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.RoomRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.RoomResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomRestMapper {

    CreateRoomCommand toCommand(RoomRequest request);

    RoomResponse toResponse(Room room);
}
