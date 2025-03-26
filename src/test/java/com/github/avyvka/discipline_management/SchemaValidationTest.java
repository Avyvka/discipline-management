package com.github.avyvka.discipline_management;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatCode;

@DataJpaTest
public class SchemaValidationTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void whenSchemaIsValid_thenEntityManagerCreatesSuccessfully() {
        assertThatCode(() -> entityManagerFactory.createEntityManager()).doesNotThrowAnyException();
    }
}