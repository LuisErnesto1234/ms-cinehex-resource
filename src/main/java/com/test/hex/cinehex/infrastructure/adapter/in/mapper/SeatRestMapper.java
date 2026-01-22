package com.test.hex.cinehex.infrastructure.adapter.in.mapper;

import com.test.hex.cinehex.domain.model.details.SeatDetails;
import com.test.hex.cinehex.domain.port.in.command.CreateSeatCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.SeatRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.SeatResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {RoomRestMapper.class})
public interface SeatRestMapper {

  CreateSeatCommand toCommand(SeatRequest seatRequest);

  @Mapping(target = "id", source = "seatDetails.id")
  @Mapping(target = "row", source = "seatDetails.row")
  @Mapping(target = "number", source = "seatDetails.number")
  @Mapping(target = "roomResponse", source = "room")
  SeatResponse toResponse(SeatDetails seatDetails);
}
