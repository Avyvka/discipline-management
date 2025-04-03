package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Discipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DisciplineRepository extends UUIDIdentifiableEntityRepository<Discipline> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"lecturer", "course"})
    Optional<Discipline> findById(@NonNull UUID uuid);

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"lecturer", "course"})
    Page<Discipline> findAll(@NonNull Pageable pageable);
}
