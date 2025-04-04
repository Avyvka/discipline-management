package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.model.entity.Discipline;
import com.github.avyvka.discipline_management.model.entity.Lecturer;
import com.github.avyvka.discipline_management.repository.CourseRepository;
import com.github.avyvka.discipline_management.repository.DisciplineRepository;
import com.github.avyvka.discipline_management.repository.LecturerRepository;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
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
class DisciplineControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DisciplineRepository disciplineRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LecturerRepository lecturerRepository;

    @PersistenceContext
    EntityManager entityManager;
    private Course course;
    private Lecturer lecturer;

    private void flushAndClearPersistenceContext() {
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenCreateDiscipline_thenShouldReturnCreatedDiscipline() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Smith");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        flushAndClearPersistenceContext();

        var response = mockMvc.perform(post("/api/v1/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "name": "Mathematics",
                                    "description": "Advanced math course",
                                    "lecturer": {
                                        "id": "%s"
                                    },
                                    "course": {
                                        "id": "%s"
                                    }
                                }
                                """, lecturer.getId(), course.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.description").value("Advanced math course"))
                .andExpect(jsonPath("$.lecturer.id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.course.id").value(course.getId().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        flushAndClearPersistenceContext();

        var id = UUID.fromString(JsonPath.parse(response).read("$.id", String.class));

        assertThat(disciplineRepository.findById(id)).isNotEmpty();
    }

    @Test
    void whenGetDisciplines_thenShouldReturnDiscipline() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Smith");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsaved = new Discipline();
        unsaved.setName("Physics");
        unsaved.setDescription("Physics course");
        unsaved.setLecturer(lecturer);
        unsaved.setCourse(course);
        var discipline = disciplineRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/disciplines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(discipline.getId().toString()))
                .andExpect(jsonPath("$.content[0].name").value("Physics"))
                .andExpect(jsonPath("$.content[0].description").value("Physics course"))
                .andExpect(jsonPath("$.content[0].lecturer.id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.content[0].course.id").value(course.getId().toString()))
                .andExpect(jsonPath("$.content[1]").doesNotExist());
    }

    @Test
    void whenGetDisciplineById_thenShouldReturnDiscipline() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Smith");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsaved = new Discipline();
        unsaved.setName("Physics");
        unsaved.setDescription("Physics course");
        unsaved.setLecturer(lecturer);
        unsaved.setCourse(course);
        var discipline = disciplineRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/disciplines/{id}", discipline.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(discipline.getId().toString()))
                .andExpect(jsonPath("$.name").value("Physics"))
                .andExpect(jsonPath("$.lecturer.id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.lecturer.firstName").value(lecturer.getFirstName()))
                .andExpect(jsonPath("$.lecturer.lastName").value(lecturer.getLastName()))
                .andExpect(jsonPath("$.lecturer.academicTitle").value(lecturer.getAcademicTitle()))
                .andExpect(jsonPath("$.course.id").value(course.getId().toString()))
                .andExpect(jsonPath("$.course.number").value(course.getNumber()));
    }

    @Test
    void whenUpdateDiscipline_thenShouldReturnUpdatedDiscipline() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Smith");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);
        var lecturer2 = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);
        var course2 = courseRepository.save(unsavedCourse);

        var unsaved = new Discipline();
        unsaved.setName("Physics");
        unsaved.setDescription("Physics course");
        unsaved.setLecturer(lecturer);
        unsaved.setCourse(course);
        var discipline = disciplineRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(put("/api/v1/disciplines/{id}", discipline.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "name": "Advanced Biology",
                                    "description": "Updated biology course",
                                    "lecturer": {
                                        "id": "%s"
                                    },
                                    "course": {
                                        "id": "%s"
                                    }
                                }
                                """, lecturer2.getId(), course2.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Biology"))
                .andExpect(jsonPath("$.description").value("Updated biology course"))
                .andExpect(jsonPath("$.lecturer.id").value(lecturer2.getId().toString()))
                .andExpect(jsonPath("$.course.id").value(course2.getId().toString()));

        flushAndClearPersistenceContext();

        var updated = disciplineRepository.findById(discipline.getId());

        assertThat(updated).isPresent().get()
                .extracting(
                        Discipline::getName,
                        Discipline::getDescription,
                        d -> d.getLecturer().getId(),
                        d -> d.getCourse().getId()
                )
                .containsExactly(
                        "Advanced Biology",
                        "Updated biology course",
                        lecturer2.getId(),
                        course2.getId()
                );
    }

    @Test
    void whenPartialUpdateDiscipline_thenShouldReturnUpdatedDiscipline() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Smith");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);
        var lecturer2 = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsaved = new Discipline();
        unsaved.setName("Physics");
        unsaved.setDescription("Physics course");
        unsaved.setLecturer(lecturer);
        unsaved.setCourse(course);
        var discipline = disciplineRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(patch("/api/v1/disciplines/{id}", discipline.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "name": "Advanced Physics",
                                    "lecturer": {
                                        "id": "%s"
                                    }
                                }
                                """, lecturer2.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Physics"))
                .andExpect(jsonPath("$.description").value("Physics course"))
                .andExpect(jsonPath("$.lecturer.id").value(lecturer2.getId().toString()))
                .andExpect(jsonPath("$.course.id").value(course.getId().toString()));

        flushAndClearPersistenceContext();

        var updated = disciplineRepository.findById(discipline.getId());

        assertThat(updated).isPresent().get()
                .extracting(
                        Discipline::getName,
                        Discipline::getDescription,
                        d -> d.getLecturer().getId(),
                        d -> d.getCourse().getId()
                )
                .containsExactly(
                        "Advanced Physics",
                        "Physics course",
                        lecturer2.getId(),
                        course.getId()
                );
    }

    @Test
    void whenDeleteDiscipline_thenShouldReturnNoContent() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Smith");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsaved = new Discipline();
        unsaved.setName("Physics");
        unsaved.setDescription("Physics course");
        unsaved.setLecturer(lecturer);
        unsaved.setCourse(course);
        var discipline = disciplineRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(delete("/api/v1/disciplines/{id}", discipline.getId()))
                .andExpect(status().isNoContent());

        flushAndClearPersistenceContext();

        assertThat(disciplineRepository.findById(discipline.getId())).isEmpty();
    }
}