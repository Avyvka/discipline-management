package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.repository.CourseRepository;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CourseRepository courseRepository;

    @PersistenceContext
    EntityManager entityManager;

    private void flushAndClearPersistenceContext() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void whenCreateCourse_thenShouldReturnCreatedCourse() throws Exception {
        var response = mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "number": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.disciplines").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();

        flushAndClearPersistenceContext();

        var id = UUID.fromString(JsonPath.parse(response).read("$.id", String.class));

        assertThat(courseRepository.findById(id)).isNotEmpty();
    }

    @Test
    void whenGetCourses_thenShouldReturnCourse() throws Exception {
        var unsaved = new Course();
        unsaved.setNumber(1);
        var course = courseRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/courses", course.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(course.getId().toString()))
                .andExpect(jsonPath("$.content[0].number").value(1))
                .andExpect(jsonPath("$.content[0].disciplines").doesNotExist())
                .andExpect(jsonPath("$.content[1]").doesNotExist());
    }

    @Test
    void whenGetCourseById_thenShouldReturnCourse() throws Exception {
        var unsaved = new Course();
        unsaved.setNumber(1);
        var course = courseRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/courses/{id}", course.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId().toString()))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.disciplines").isEmpty());
    }

    @Test
    void whenUpdateCourse_thenShouldReturnUpdatedCourse() throws Exception {
        var unsaved = new Course();
        unsaved.setNumber(1);
        var course = courseRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(put("/api/v1/courses/{id}", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "number": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId().toString()))
                .andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.disciplines").isEmpty());

        flushAndClearPersistenceContext();

        var updated = courseRepository.findById(course.getId());

        assertThat(updated).isPresent().get()
                .extracting(Course::getNumber).isEqualTo(2);
    }

    @Test
    void whenPartialUpdateCourse_thenShouldReturnUpdatedCourse() throws Exception {
        var unsaved = new Course();
        unsaved.setNumber(1);
        var course = courseRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(patch("/api/v1/courses/{id}", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "number": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId().toString()))
                .andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.disciplines").isEmpty());

        flushAndClearPersistenceContext();

        var updated = courseRepository.findById(course.getId());

        assertThat(updated).isPresent().get()
                .extracting(Course::getNumber).isEqualTo(2);
    }

    @Test
    void whenDeleteCourse_thenShouldReturnNoContent() throws Exception {
        var unsaved = new Course();
        unsaved.setNumber(1);
        var course = courseRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(delete("/api/v1/courses/{id}", course.getId()))
                .andExpect(status().isNoContent());

        flushAndClearPersistenceContext();

        assertThat(courseRepository.findById(course.getId())).isEmpty();
    }
}