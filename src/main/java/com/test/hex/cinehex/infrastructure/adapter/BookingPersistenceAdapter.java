package com.test.hex.cinehex.infrastructure.adapter;

import com.test.hex.cinehex.domain.enums.BookingStatus;
import com.test.hex.cinehex.domain.model.Booking;
import com.test.hex.cinehex.domain.port.out.BookingRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper.BookingEntityMapper;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.BookingJpaRepository;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.MovieShowJpaRepository;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.SeatJpaRepository;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingPersistenceAdapter implements BookingRepositoryPort {

  private final BookingJpaRepository bookingJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final MovieShowJpaRepository movieShowJpaRepository;
  private final BookingEntityMapper bookingEntityMapper;
  private final SeatJpaRepository seatJpaRepository;

  @Override
  public Booking save(Booking booking) {

    var userEntity =
        userJpaRepository
            .findById(booking.userId())
            .orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + booking.userId()));

    var movieShowEntity =
        movieShowJpaRepository
            .findById(booking.movieShowId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "MovieShow not found with id: " + booking.movieShowId()));

    var seatEntities = seatJpaRepository.findAllById(booking.seatsIds());

    var bookingEntity =
        bookingEntityMapper.toEntity(booking, userEntity, movieShowEntity, seatEntities);

    var savedBookingEntity = bookingJpaRepository.save(bookingEntity);

    return bookingEntityMapper.toDomain(savedBookingEntity);
  }

  @Override
  public boolean existsById(UUID id) {
    return bookingJpaRepository.existsById(id);
  }

  @Override
  public List<UUID> findOccupiedSeats(UUID movieShowId, List<UUID> seatsIds) {

    List<BookingStatus> occupiedStatuses = List.of(BookingStatus.CONFIRMED, BookingStatus.PENDING);

    return bookingJpaRepository.findOccupiedSeatIds(movieShowId, seatsIds, occupiedStatuses);
  }

  @Override
  public int expireOldReservations(Instant limit) {
    return bookingJpaRepository.expireOldReservations(limit);
  }

  @Override
  public Optional<Booking> findById(UUID bookingId) {
    return bookingJpaRepository.findById(bookingId).map(bookingEntityMapper::toDomain);
  }
}
