package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.UUIDIdentifiableEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface UUIDIdentifiableEntityRepository<T extends UUIDIdentifiableEntity> extends IdentifiableJpaRepository<T, UUID> {
}