package com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository;

import com.test.hex.cinehex.domain.enums.BookingStatus;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.BookingEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingJpaRepository extends JpaRepository<@NonNull BookingEntity, @NonNull UUID> {

  @Query(
      """
        SELECT s.id
        FROM BookingEntity b
        JOIN b.seatEntities s
        WHERE b.movieShowEntity.id = :movieShowId
          AND s.id IN :seatsIds
          AND b.bookingStatus IN :statuses
    """)
  List<UUID> findOccupiedSeatIds(
      @Param("movieShowId") UUID movieShowId,
      @Param("seatsIds") List<UUID> seatsIds,
      @Param("statuses") List<BookingStatus> statuses);

  @Modifying
  @Query(
      """
        UPDATE BookingEntity b SET b.bookingStatus = 'EXPIRED'
        WHERE b.bookingStatus = 'RESERVED' AND b.createdAt < :limit
    """)
  int expireOldReservations(Instant limit);
}
