package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.controller.support.AbstractCrudController;
import com.github.avyvka.discipline_management.model.Identifiable;
import com.github.avyvka.discipline_management.service.support.AbstractCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractCrudControllerTest {

    @Mock
    AbstractCrudService<Entity, Dto, Id> crudService;
    AbstractCrudController<Dto, Id> crudController;

    @Test
    void whenCreate_thenReturnsCreatedResponseWithLocation() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);

        when(id.toString()).thenReturn(UUID.randomUUID().toString());
        when(dto.getId()).thenReturn(id);
        when(crudService.create(dto)).thenReturn(dto);

        var response = crudController.create(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(dto);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(response.getHeaders().getLocation().toString()).endsWith("/" + id);
    }

    @Test
    void whenGetById_thenReturnsOkWithDto() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);

        when(crudService.findById(id)).thenReturn(Optional.of(dto));

        var response = crudController.getById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Mock
    MockHttpServletRequest mockHttpServletRequest;

    @Test
    void whenGetByIdNotFound_thenReturnsNotFound() {
        var id = mock(Id.class);

        when(crudService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = crudController.getById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        crudController = mock(
                AbstractCrudController.class,
                withSettings()
                        .useConstructor(crudService)
                        .defaultAnswer(CALLS_REAL_METHODS)
        );

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));
    }

    @Test
    void whenGetAll_thenReturnsPageOfDto() {
        var dto = mock(Dto.class);
        var pageable = mock(Pageable.class);

        when(crudService.findAll(pageable)).thenReturn(new PageImpl<>(List.of(dto)));

        var response = crudController.getAll(pageable);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent())
                .hasSize(1)
                .first()
                .isEqualTo(dto);
    }

    @Test
    void whenUpdate_thenReturnsUpdatedDto() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);

        when(crudService.update(id, dto)).thenReturn(dto);

        var response = crudController.update(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void whenPartialUpdate_thenReturnsPatchedDto() {
        var id = mock(Id.class);
        var dto = mock(Dto.class);

        when(crudService.partialUpdate(id, dto)).thenReturn(dto);

        var response = crudController.partialUpdate(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void whenDelete_thenReturnsNoContent() {
        var id = mock(Id.class);

        doNothing().when(crudService).delete(id);

        var response = crudController.delete(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(crudService).delete(id);
    }

    interface Id extends Serializable {
    }

    interface Entity extends Identifiable<Id> {
    }

    interface Dto extends Identifiable<Id> {
    }
}