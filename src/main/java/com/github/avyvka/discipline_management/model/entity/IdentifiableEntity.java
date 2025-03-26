package com.github.avyvka.discipline_management.model.entity;

import com.github.avyvka.discipline_management.support.CompactId;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.Id;

@MappedSuperclass
public abstract class IdentifiableEntity {

    @Id
    @Column(updatable = false, nullable = false, length = 32)
    private String id;

    public IdentifiableEntity() {
        this.id = CompactId.randomId(this.getClass()).toString();
    }

    public String getId() {
        return id;
    }
}
