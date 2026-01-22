package com.test.hex.cinehex.infrastructure.adapter.in.mapper;

import com.test.hex.cinehex.domain.model.details.BookingReceipt;
import com.test.hex.cinehex.domain.port.in.command.CreateBookingCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.BookingRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {UserRestMapper.class, MovieShowRestMapper.class, SeatRestMapper.class})
public interface BookingRestMapper {

  @Mapping(target = "userId", source = "request.userId")
  @Mapping(target = "movieShowId", source = "request.movieShowId")
  @Mapping(target = "seatsIds", source = "request.seatsIds")
  CreateBookingCommand toCommand(BookingRequest request);

  @Mapping(target = "id", source = "booking.id")
  @Mapping(target = "userResponse", source = "user")
  @Mapping(target = "movieShowResponse", source = "movieShow")
  @Mapping(target = "seatResponses", source = "seats")
  @Mapping(target = "totalAmount", source = "booking.totalAmount")
  @Mapping(target = "bookingStatus", source = "booking.bookingStatus")
  @Mapping(target = "createdAt", source = "booking.createdAt")
  BookingResponse toResponse(
      BookingReceipt booking);
}
