package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Discipline;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends IdentifiableEntityRepository<Discipline> {
}
