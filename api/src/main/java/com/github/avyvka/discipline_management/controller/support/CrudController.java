package com.github.avyvka.discipline_management.controller.support;

import com.github.avyvka.discipline_management.model.Identifiable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public interface CrudController<D extends Identifiable<ID>, ID> {

    @PostMapping
    ResponseEntity<D> create(@Validated @RequestBody D dto);

    @GetMapping("/{id}")
    ResponseEntity<D> getById(@PathVariable ID id);

    @GetMapping
    ResponseEntity<PagedModel<D>> getAll(Pageable pageable);

    @PutMapping("/{id}")
    ResponseEntity<D> update(@PathVariable ID id, @Validated @RequestBody D dto);

    @PatchMapping("/{id}")
    ResponseEntity<D> partialUpdate(@PathVariable ID id, @Validated @RequestBody D dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable ID id);
}
