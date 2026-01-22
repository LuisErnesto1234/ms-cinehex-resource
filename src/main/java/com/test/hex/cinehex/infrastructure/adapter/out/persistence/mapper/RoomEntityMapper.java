package com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomEntityMapper {

  Room toDomain(RoomEntity roomEntity);

  RoomEntity toEntity(Room room);
}
