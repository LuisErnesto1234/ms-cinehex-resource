package com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository;

import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.SeatEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeatJpaRepository extends JpaRepository<@NonNull SeatEntity, @NonNull UUID> {
  @Query(
      "SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END "
          + "FROM SeatEntity s "
          + "WHERE s.row = :row AND s.number = :number AND s.roomEntity.id = :roomId")
  Boolean existsByRowAndNumberAndRoomId(String row, Integer number, UUID roomId);
}
