package com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.Persistable;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "movies")
@Entity
public class MovieEntity implements Persistable<UUID> {

  @Id private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "duration_minutes", nullable = false)
  private Integer durationMinutes;

  @OneToMany(mappedBy = "movieEntity", orphanRemoval = true)
  private List<MovieShowEntity> movieShowEntities;

  // 2. Campo transitorio (no se guarda en BD) para controlar el estado
  @Transient private boolean isNew = true;

  // 3. Método de la interfaz: Spring llama a esto para decidir si hace INSERT o UPDATE
  @Override
  public boolean isNew() {
    return isNew; // Si es true, fuerza INSERT. Si es false, hace UPDATE.
  }

  @PostLoad
  @PrePersist
  public void markNotNew() {
    this.isNew = false; // Después de cargar o antes de persistir, marcamos como no nuevo
  }
}
