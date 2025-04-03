package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdentifiableJpaRepository<T extends Identifiable<ID>, ID> extends JpaRepository<T, ID> {
}