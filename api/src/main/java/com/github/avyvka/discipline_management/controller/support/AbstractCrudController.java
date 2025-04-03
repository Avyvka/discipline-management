package com.github.avyvka.discipline_management.controller.support;

import com.github.avyvka.discipline_management.model.Identifiable;
import com.github.avyvka.discipline_management.service.support.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractCrudController<D extends Identifiable<ID>, ID> implements CrudController<D, ID> {

    private final CrudService<D, ID> service;

    protected AbstractCrudController(CrudService<D, ID> service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<D> create(@Validated @RequestBody D dto) {
        var created = service.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @Override
    public ResponseEntity<D> getById(@PathVariable ID id) {
        return ResponseEntity.of(service.findById(id));
    }

    @Override
    public ResponseEntity<Page<D>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Override
    public ResponseEntity<D> update(@PathVariable ID id, @Validated @RequestBody D dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Override
    public ResponseEntity<D> partialUpdate(@PathVariable ID id, @Validated @RequestBody D dto) {
        return ResponseEntity.ok(service.partialUpdate(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}