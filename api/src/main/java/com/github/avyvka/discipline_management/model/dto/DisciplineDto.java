package com.github.avyvka.discipline_management.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.avyvka.discipline_management.model.IdentifiableRecord;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DisciplineDto(
        UUID id,
        String name,
        String description,
        LecturerDto lecturer,
        CourseDto course
) implements IdentifiableRecord<UUID> {
}