package com.test.hex.cinehex.infrastructure.utils;

import com.test.hex.cinehex.domain.port.out.BookingRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class BookingCleanupSchedule {

  private static final Logger log = LoggerFactory.getLogger(BookingCleanupSchedule.class);
  private final BookingRepositoryPort bookingRepositoryPort;

  @Scheduled(fixedRate = 60000) // Runs every 60 seconds
  @Transactional
  public void cleanupExpiredBookings() {

    int count =
        bookingRepositoryPort.expireOldReservations(Instant.now().minus(10, ChronoUnit.MINUTES));

    if (count > 0) {
      BookingCleanupSchedule.log.info(
          "🧹 Limpieza: Se expiraron {}, {}", count, " reservas antiguas.");
    }
  }
}
