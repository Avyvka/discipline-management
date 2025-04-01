package com.github.avyvka.discipline_management.model.dto;

import java.util.UUID;

public record StudentDto(
        UUID id,
        String firstName,
        String lastName,
        String middleName,
        Integer age,
        String group,
        CourseDto course
) implements IdentifiableEntityDto<UUID> {
}