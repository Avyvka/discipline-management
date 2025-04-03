package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.controller.support.AbstractCrudController;
import com.github.avyvka.discipline_management.model.Identifiable;
import com.github.avyvka.discipline_management.service.support.AbstractCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractCrudControllerTest {

    @Mock
    AbstractCrudService<Identifiable<Object>, Identifiable<Object>, Object> crudService;

    @Mock
    MockHttpServletRequest mockHttpServletRequest;

    AbstractCrudController<Identifiable<Object>, Object> crudController;

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
    void whenCreate_thenReturnsCreatedResponseWithLocation() {
        var id = UUID.randomUUID().toString();
        var dto = mock(Identifiable.class);

        when(dto.getId()).thenReturn(id);
        when(crudService.create(dto)).thenReturn(dto);

        @SuppressWarnings("unchecked")
        ResponseEntity<?> response = crudController.create(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(dto);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(response.getHeaders().getLocation().toString()).endsWith("/" + id);
    }

    @Test
    void whenGetById_thenReturnsOkWithDto() {
        var id = mock(Object.class);
        var dto = mock(Identifiable.class);

        when(crudService.findById(id)).thenReturn(Optional.of(dto));

        ResponseEntity<?> response = crudController.getById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void whenGetByIdNotFound_thenReturnsNotFound() {
        var id = mock(Object.class);

        when(crudService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = crudController.getById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void whenGetAll_thenReturnsPageOfDto() {
        var dto = mock(Identifiable.class);
        var pageable = mock(Pageable.class);

        when(crudService.findAll(pageable)).thenReturn(new PageImpl<>(List.of(dto)));

        ResponseEntity<Page<Identifiable<Object>>> response = crudController.getAll(pageable);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasSize(1)
                .first()
                .isEqualTo(dto);
    }

    @Test
    void whenUpdate_thenReturnsUpdatedDto() {
        var id = mock(Object.class);
        var dto = mock(Identifiable.class);

        when(crudService.update(id, dto)).thenReturn(dto);

        @SuppressWarnings("unchecked")
        ResponseEntity<?> response = crudController.update(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void whenPartialUpdate_thenReturnsPatchedDto() {
        var id = mock(Object.class);
        var dto = mock(Identifiable.class);

        when(crudService.partialUpdate(id, dto)).thenReturn(dto);

        @SuppressWarnings("unchecked")
        ResponseEntity<?> response = crudController.partialUpdate(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void whenDelete_thenReturnsNoContent() {
        var id = mock(Object.class);

        doNothing().when(crudService).delete(id);

        ResponseEntity<Void> response = crudController.delete(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(crudService).delete(id);
    }
}