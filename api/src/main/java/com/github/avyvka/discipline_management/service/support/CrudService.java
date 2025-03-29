package com.github.avyvka.discipline_management.service.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudService<D, ID> {

    D create(D dto);

    Optional<D> findById(ID id);

    Page<D> findAll(Pageable pageable);

    D update(ID id, D dto);

    D partialUpdate(ID id, D dto);

    void delete(ID id);
}