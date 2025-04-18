package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends PersonRepository<Student> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"course"})
    Optional<Student> findById(@NonNull UUID uuid);

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"course"})
    Page<Student> findAll(@NonNull Pageable pageable);
}
