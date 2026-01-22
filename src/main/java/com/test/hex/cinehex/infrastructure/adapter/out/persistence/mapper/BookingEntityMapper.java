package com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper;

import com.test.hex.cinehex.domain.model.Booking;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.BookingEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieShowEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.SeatEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingEntityMapper {

  @Mapping(target = "movieShowId", source = "bookingEntity.movieShowEntity.id")
  @Mapping(target = "userId", source = "bookingEntity.userEntity.id")
  @Mapping(target = "seatsIds", source = "bookingEntity.seatEntities")
  Booking toDomain(BookingEntity bookingEntity);

  @Mapping(target = "isNew", ignore = true)
  @Mapping(target = "id", source = "booking.id")
  @Mapping(target = "bookingStatus", source = "booking.bookingStatus")
  @Mapping(target = "totalAmount", source = "booking.totalAmount")
  @Mapping(target = "createdAt", source = "booking.createdAt")
  @Mapping(target = "movieShowEntity", source = "movieShowEntity")
  @Mapping(target = "userEntity", source = "userEntity")
  @Mapping(target = "seatEntities", source = "seatEntities")
  BookingEntity toEntity(
      Booking booking,
      UserEntity userEntity,
      MovieShowEntity movieShowEntity,
      List<SeatEntity> seatEntities);

  default List<UUID> map(List<SeatEntity> seatEntities) {
    return seatEntities.stream().map(SeatEntity::getId).toList();
  }
}
