package com.github.avyvka.discipline_management.service.support;

import com.github.avyvka.discipline_management.mapper.EntityDtoMapper;
import com.github.avyvka.discipline_management.model.Identifiable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractCrudServiceTest {

    @Mock
    JpaRepository<Entity, Id> repository;
    @Mock
    EntityDtoMapper<Entity, Dto> mapper;
    AbstractCrudService<Entity, Dto, Id> crudService;

    @Test
    void whenCreatingEntity_thenShouldReturnSavedDto() {
        var dto = mock(Dto.class);
        var entity = mock(Entity.class);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        var result = crudService.create(dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
        verify(mapper).toDto(entity);
    }

    @Test
    void whenEntityExists_thenShouldReturnDto() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);
        var entity = mock(Entity.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        assertThat(crudService.findById(id))
                .isPresent()
                .contains(dto);
    }

    @Test
    void whenEntityNotExists_thenShouldReturnEmptyOptional() {
        var id = mock(Id.class);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThat(crudService.findById(id)).isEmpty();
    }

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        crudService = mock(
                AbstractCrudService.class,
                withSettings()
                        .useConstructor(repository, mapper)
                        .defaultAnswer(CALLS_REAL_METHODS)
        );
    }

    @Test
    void whenFindAllWithPageable_thenShouldReturnPageOfDto() {
        var pageable = mock(Pageable.class);
        var dto = mock(Dto.class);
        var entity = mock(Entity.class);

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDtoLazy(entity)).thenReturn(dto);

        assertThat(crudService.findAll(pageable))
                .hasSize(1)
                .first()
                .isEqualTo(dto);
    }

    @Test
    void whenUpdatingExistingEntity_thenShouldReturnUpdatedDto() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);
        var entity = mock(Entity.class);
        var updatedDto = mock(Dto.class);
        var updatedEntity = mock(Entity.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.update(entity, dto)).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);

        Object result = crudService.update(id, dto);

        assertThat(result).isEqualTo(updatedDto);
    }

    @Test
    void whenUpdatingNonExistingEntity_thenShouldThrowNotFoundException() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> crudService.update(id, dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                .hasMessageContaining("Entity with ID " + id + " not found");
    }

    @Test
    void whenPartiallyUpdatingExistingEntity_thenShouldReturnPatchedDto() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);
        var entity = mock(Entity.class);
        var updatedDto = mock(Dto.class);
        var updatedEntity = mock(Entity.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.partialUpdate(entity, dto)).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);

        Object result = crudService.partialUpdate(id, dto);

        assertThat(result).isEqualTo(updatedDto);
    }

    @Test
    void whenPartiallyUpdatingNonExistingEntity_thenShouldThrowNotFoundException() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> crudService.partialUpdate(id, dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
    }

    @Test
    void whenDeletingEntity_thenShouldCallRepositoryDelete() {
        var id = mock(Id.class);

        crudService.delete(id);

        verify(repository).deleteById(id);
    }

    interface Id extends Serializable {
    }

    interface Entity extends Identifiable<Id> {
    }

    interface Dto extends Identifiable<Id> {
    }
}