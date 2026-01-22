package com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(
    name = "movie_shows",
    indexes = {
      @Index(name = "idx_room_id", columnList = "room_id"),
      @Index(
          name = "idx_time_range",
          columnList = "start_time, end_time") // Índice compuesto es mejor para rangos
    })
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieShowEntity implements Persistable<@NonNull UUID> {

  @Id private UUID id;

  @Column(name = "start_time", nullable = false)
  private Instant startTime;

  @Column(name = "end_time", nullable = false)
  private Instant endTime;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id", nullable = false)
  private MovieEntity movieEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private RoomEntity roomEntity;

  @OneToMany(
      mappedBy = "movieShowEntity",
      fetch = FetchType.LAZY,
      cascade =
          CascadeType.ALL, // Aquí sí está bien: Si borro la función, borro sus reservas (opcional)
      orphanRemoval = true)
  private List<BookingEntity> bookingEntities = new ArrayList<>();

  // --- Lógica Persistable ---
  @Transient private boolean isNew = true;

  @Override
  public boolean isNew() {
    return isNew;
  }

  @PrePersist
  @PostLoad
  public void markNotNew() {
    this.isNew = false;
  }
}
