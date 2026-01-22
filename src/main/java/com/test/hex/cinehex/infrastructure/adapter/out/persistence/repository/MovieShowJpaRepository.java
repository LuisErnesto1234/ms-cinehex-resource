package com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository;

import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieShowEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface MovieShowJpaRepository
    extends JpaRepository<@NonNull MovieShowEntity, @NonNull UUID> {

    @Query("""
        SELECT CASE WHEN COUNT(ms) > 0 THEN TRUE ELSE FALSE END
        FROM MovieShowEntity ms
        WHERE ms.roomEntity.id = :roomId
          AND ms.startTime < :newEndTime
          AND ms.endTime > :newStartTime
    """)
  Boolean existsShowInRoomWithTimeOverlap(UUID roomId, Instant newStartTime, Instant newEndTime);
}
