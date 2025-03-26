package com.github.avyvka.discipline_management.model.dto;

public record StudentDto(
        String id,
        String firstName,
        String lastName,
        String middleName,
        int age,
        String group,
        CourseDto course
) implements IdentifiableEntityDto<String> {
}