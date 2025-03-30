package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.IdentifiableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdentifiableEntityRepository<T extends IdentifiableEntity> extends JpaRepository<T, java.util.UUID> {
}