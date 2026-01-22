package com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper;

import com.test.hex.cinehex.domain.model.Seat;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.SeatEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeatEntityMapper {

  @Mapping(target = "id", source = "seatEntity.id")
  @Mapping(target = "row", source = "seatEntity.row")
  @Mapping(target = "number", source = "seatEntity.number")
  @Mapping(target = "roomId", source = "seatEntity.roomEntity.id")
  Seat toDomain(SeatEntity seatEntity);

  @Mapping(target = "id", source = "seat.id")
  @Mapping(target = "row", source = "seat.row")
  @Mapping(target = "number", source = "seat.number")
  @Mapping(target = "roomEntity", source = "roomEntity")
  SeatEntity toEntity(Seat seat, RoomEntity roomEntity);
}
