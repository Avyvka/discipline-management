package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PersonRepository<Student> {
}
