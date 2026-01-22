package com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.domain.Persistable;

import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity implements Persistable<UUID> {

  @Id private UUID id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 100, unique = true)
  private String email;

  @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<BookingEntity> bookingEntities;

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
