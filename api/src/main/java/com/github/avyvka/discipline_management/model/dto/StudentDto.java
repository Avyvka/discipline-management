package com.github.avyvka.discipline_management.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
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