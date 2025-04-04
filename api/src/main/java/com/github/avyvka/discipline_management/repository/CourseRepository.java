package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends UUIDIdentifiableEntityRepository<Course> {

    @EntityGraph(attributePaths = {"disciplines"})
    @NonNull
    @Override
    Optional<Course> findById(@NonNull UUID uuid);
}
