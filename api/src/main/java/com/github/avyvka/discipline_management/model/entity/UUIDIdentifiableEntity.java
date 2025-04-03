package com.github.avyvka.discipline_management.model.entity;

import com.github.avyvka.discipline_management.model.Identifiable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class UUIDIdentifiableEntity implements Identifiable<UUID> {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
