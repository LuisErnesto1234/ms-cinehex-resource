package com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity;

import com.test.hex.cinehex.domain.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "bookings")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingEntity implements Persistable<@NonNull UUID> {

  @Id private UUID id;

  @Version private Long version;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "booking_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private BookingStatus bookingStatus;

  @Column(name = "total_amount", nullable = false)
  private String totalAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_show_id", nullable = false)
  private MovieShowEntity movieShowEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "booking_seats",
      joinColumns = @JoinColumn(name = "booking_id"),
      inverseJoinColumns = @JoinColumn(name = "seat_id"))
  private List<SeatEntity> seatEntities = new ArrayList<>();

  @Transient @Builder.Default private boolean isNew = true;

  @Override
  public boolean isNew() {
    return false;
  }

  @PrePersist
  @PostLoad
  public void markNotNew() {
    this.isNew = false;
  }
}
