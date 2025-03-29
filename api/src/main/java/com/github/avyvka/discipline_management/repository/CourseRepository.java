package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends IdentifiableEntityRepository<Course> {
}
