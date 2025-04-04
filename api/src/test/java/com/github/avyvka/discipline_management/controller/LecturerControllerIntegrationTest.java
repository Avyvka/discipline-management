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
class LecturerControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LecturerRepository lecturerRepository;

    @Autowired
    DisciplineRepository disciplineRepository;

    @Autowired
    CourseRepository courseRepository;

    @PersistenceContext
    EntityManager entityManager;

    private void flushAndClearPersistenceContext() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void whenCreateLecturer_thenShouldReturnCreatedLecturer() throws Exception {
        var response = mockMvc.perform(post("/api/v1/lecturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "middleName": "Michael",
                                    "age": 35,
                                    "academicTitle": "Professor"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.middleName").value("Michael"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.academicTitle").value("Professor"))
                .andExpect(jsonPath("$.disciplines").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();

        flushAndClearPersistenceContext();

        var id = UUID.fromString(JsonPath.parse(response).read("$.id", String.class));

        assertThat(lecturerRepository.findById(id)).isNotEmpty();
    }

    @Test
    void whenGetLecturers_thenShouldReturnLecturer() throws Exception {
        var unsaved = new Lecturer();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Michael");
        unsaved.setAge(35);
        unsaved.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/lecturers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.content[0].middleName").value("Michael"))
                .andExpect(jsonPath("$.content[0].age").value(35))
                .andExpect(jsonPath("$.content[0].academicTitle").value("Professor"))
                .andExpect(jsonPath("$.content[0].disciplines").doesNotExist())
                .andExpect(jsonPath("$.content[1]").doesNotExist());
    }

    @Test
    void whenGetLecturerById_thenShouldReturnLecturer() throws Exception {
        var unsavedLecturer = new Lecturer();
        unsavedLecturer.setFirstName("John");
        unsavedLecturer.setLastName("Doe");
        unsavedLecturer.setMiddleName("Michael");
        unsavedLecturer.setAge(35);
        unsavedLecturer.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsavedLecturer);

        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsavedDiscipline = new Discipline();
        unsavedDiscipline.setName("Physics");
        unsavedDiscipline.setDescription("Physics course");
        unsavedDiscipline.setLecturer(lecturer);
        unsavedDiscipline.setCourse(course);
        var discipline = disciplineRepository.save(unsavedDiscipline);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/lecturers/{id}", lecturer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.middleName").value("Michael"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.academicTitle").value("Professor"))
                .andExpect(jsonPath("$.disciplines[0].id").value(discipline.getId().toString()))
                .andExpect(jsonPath("$.disciplines[0].name").value("Physics"))
                .andExpect(jsonPath("$.disciplines[0].description").value("Physics course"))
                .andExpect(jsonPath("$.disciplines[0].lecturer").doesNotExist())
                .andExpect(jsonPath("$.disciplines[0].course").doesNotExist())
                .andExpect(jsonPath("$.disciplines[1].id").doesNotExist());

    }

    @Test
    void whenUpdateLecturer_thenShouldReturnUpdatedLecturer() throws Exception {
        var unsaved = new Lecturer();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Michael");
        unsaved.setAge(35);
        unsaved.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(put("/api/v1/lecturers/{id}", lecturer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Jane",
                                    "lastName": "Smith",
                                    "middleName": "Doe",
                                    "age": 21,
                                    "academicTitle": "Dr."
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.middleName").value("Doe"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.academicTitle").value("Dr."));

        flushAndClearPersistenceContext();

        var updated = lecturerRepository.findById(lecturer.getId());

        assertThat(updated).isPresent().get()
                .extracting(
                        Lecturer::getFirstName,
                        Lecturer::getLastName,
                        Lecturer::getMiddleName,
                        Lecturer::getAge,
                        Lecturer::getAcademicTitle
                )
                .containsExactly("Jane", "Smith", "Doe", 21, "Dr.");
    }

    @Test
    void whenPartialUpdateLecturer_thenShouldReturnUpdatedLecturer() throws Exception {
        var unsaved = new Lecturer();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Michael");
        unsaved.setAge(35);
        unsaved.setAcademicTitle("Professor");
        var lecturer = lecturerRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(patch("/api/v1/lecturers/{id}", lecturer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "middleName": "Smith",
                                    "academicTitle": "Associate Professor"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lecturer.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.middleName").value("Smith"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.academicTitle").value("Associate Professor"));

        flushAndClearPersistenceContext();

        var updated = lecturerRepository.findById(lecturer.getId());

        assertThat(updated).isPresent().get()
                .extracting(
                        Lecturer::getFirstName,
                        Lecturer::getLastName,
                        Lecturer::getMiddleName,
                        Lecturer::getAge,
                        Lecturer::getAcademicTitle
                )
                .containsExactly("John", "Doe", "Smith", 35, "Associate Professor");
    }

    @Test
    void whenDeleteLecturer_thenShouldReturnNoContent() throws Exception {
        var unsaved = new Lecturer();
        unsaved.setFirstName("ToDelete");
        unsaved.setLastName("Lecturer");
        var lecturer = lecturerRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(delete("/api/v1/lecturers/{id}", lecturer.getId()))
                .andExpect(status().isNoContent());

        flushAndClearPersistenceContext();

        assertThat(lecturerRepository.findById(lecturer.getId())).isEmpty();
    }
}