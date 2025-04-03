package com.github.avyvka.discipline_management.service.support;

import com.github.avyvka.discipline_management.mapper.EntityDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class AbstractCrudService<E, D, ID> implements CrudService<D, ID> {

    private final JpaRepository<E, ID> repository;

    private final EntityDtoMapper<E, D> mapper;

    protected AbstractCrudService(JpaRepository<E, ID> repository, EntityDtoMapper<E, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public D create(D dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public Optional<D> findById(ID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public Page<D> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDtoLazy);
    }

    @Override
    public D update(ID id, D dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with ID " + id + " not found"));

        return mapper.toDto(repository.save(mapper.update(entity, dto)));
    }

    @Override
    public D partialUpdate(ID id, D dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with ID " + id + " not found"));

        return mapper.toDto(repository.save(mapper.partialUpdate(entity, dto)));
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
