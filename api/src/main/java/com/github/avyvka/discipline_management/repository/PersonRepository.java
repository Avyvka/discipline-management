package com.github.avyvka.discipline_management.repository;

import com.github.avyvka.discipline_management.model.entity.Person;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository<T extends Person> extends UUIDIdentifiableEntityRepository<T> {
}
