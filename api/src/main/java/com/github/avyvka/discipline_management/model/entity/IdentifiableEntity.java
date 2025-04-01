package com.github.avyvka.discipline_management.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class IdentifiableEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    public IdentifiableEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
