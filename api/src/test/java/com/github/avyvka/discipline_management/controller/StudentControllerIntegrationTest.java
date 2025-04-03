package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.model.entity.Student;
import com.github.avyvka.discipline_management.repository.CourseRepository;
import com.github.avyvka.discipline_management.repository.StudentRepository;
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
class StudentControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @PersistenceContext
    EntityManager entityManager;

    private void flushAndClearPersistenceContext() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void whenCreateStudent_thenShouldReturnCreatedStudent() throws Exception {
        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        flushAndClearPersistenceContext();

        var response = mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "middleName": "Smith",
                                    "age": 20,
                                    "group": "CS-101",
                                    "course": {
                                        "id": "%s"
                                    }
                                }
                                """, course.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.middleName").value("Smith"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.group").value("CS-101"))
                .andExpect(jsonPath("$.course.id").value(course.getId().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        flushAndClearPersistenceContext();

        var id = UUID.fromString(JsonPath.parse(response).read("$.id", String.class));

        assertThat(studentRepository.findById(id)).isNotEmpty();
    }

    @Test
    void whenGetStudents_thenShouldReturnStudent() throws Exception {
        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsaved = new Student();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Smith");
        unsaved.setAge(20);
        unsaved.setGroup("CS-101");
        unsaved.setCourse(course);
        var student = studentRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(student.getId().toString()))
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.content[0].middleName").value("Smith"))
                .andExpect(jsonPath("$.content[0].age").value(20))
                .andExpect(jsonPath("$.content[0].group").value("CS-101"))
                .andExpect(jsonPath("$.content[0].course.id").value(course.getId().toString()))
                .andExpect(jsonPath("$.content[1]").doesNotExist());
    }

    @Test
    void whenGetStudentById_thenShouldReturnStudent() throws Exception {
        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);

        var unsaved = new Student();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Smith");
        unsaved.setAge(20);
        unsaved.setGroup("CS-101");
        unsaved.setCourse(course);
        var student = studentRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(get("/api/v1/students/{id}", student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.middleName").value("Smith"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.group").value("CS-101"))
                .andExpect(jsonPath("$.course.id").value(course.getId().toString()));
    }

    @Test
    void whenUpdateStudent_thenShouldReturnUpdatedStudent() throws Exception {
        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);
        var course2 = courseRepository.save(unsavedCourse);

        var unsaved = new Student();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Smith");
        unsaved.setAge(20);
        unsaved.setGroup("CS-101");
        unsaved.setCourse(course);
        var student = studentRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(put("/api/v1/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "firstName": "Jane",
                                    "lastName": "Smith",
                                    "middleName": "Doe",
                                    "age": 21,
                                    "group": "CS-102",
                                    "course": {
                                      "id": "%s"
                                    }
                                }
                                """, course2.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.middleName").value("Doe"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.group").value("CS-102"))
                .andExpect(jsonPath("$.course.id").value(course2.getId().toString()));

        flushAndClearPersistenceContext();

        var updated = studentRepository.findById(student.getId());

        assertThat(updated).isPresent().get()
                .extracting(
                        Student::getFirstName,
                        Student::getLastName,
                        Student::getMiddleName,
                        Student::getAge,
                        Student::getGroup,
                        e -> e.getCourse().getId()
                )
                .containsExactly("Jane", "Smith", "Doe", 21, "CS-102", course2.getId());
    }

    @Test
    void whenPartialUpdateStudent_thenShouldReturnUpdatedStudent() throws Exception {
        var unsavedCourse = new Course();
        unsavedCourse.setNumber(1);
        var course = courseRepository.save(unsavedCourse);
        var course2 = courseRepository.save(unsavedCourse);

        var unsaved = new Student();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Smith");
        unsaved.setAge(20);
        unsaved.setGroup("CS-101");
        unsaved.setCourse(course);
        var student = studentRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(patch("/api/v1/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "age": 21,
                                    "group": "CS-102",
                                    "course": {
                                      "id": "%s"
                                    }
                                }
                                """, course2.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.middleName").value("Smith"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.group").value("CS-102"))
                .andExpect(jsonPath("$.course.id").value(course2.getId().toString()));

        flushAndClearPersistenceContext();

        var updated = studentRepository.findById(student.getId());

        assertThat(updated).isPresent().get()
                .extracting(
                        Student::getFirstName,
                        Student::getLastName,
                        Student::getMiddleName,
                        Student::getAge,
                        Student::getGroup,
                        e -> e.getCourse().getId()
                )
                .containsExactly("John", "Doe", "Smith", 21, "CS-102", course2.getId());
    }

    @Test
    void whenDeleteStudent_thenShouldReturnNoContent() throws Exception {
        var unsaved = new Student();
        unsaved.setFirstName("John");
        unsaved.setLastName("Doe");
        unsaved.setMiddleName("Smith");
        unsaved.setAge(20);
        unsaved.setGroup("CS-101");

        flushAndClearPersistenceContext();

        var student = studentRepository.save(unsaved);

        flushAndClearPersistenceContext();

        mockMvc.perform(delete("/api/v1/students/{id}", student.getId()))
                .andExpect(status().isNoContent());

        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }
}