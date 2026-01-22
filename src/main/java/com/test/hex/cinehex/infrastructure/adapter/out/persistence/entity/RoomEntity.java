package com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "rooms")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomEntity {
  @Id private UUID id;

  private String name;
  private Integer capacity;

  @OneToMany(mappedBy = "roomEntity", orphanRemoval = true)
  private List<SeatEntity> seatEntities;

  @OneToMany(mappedBy = "roomEntity", orphanRemoval = true)
  private List<MovieShowEntity> movieShowEntities;
}
