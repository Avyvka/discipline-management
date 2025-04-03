package com.github.avyvka.discipline_management.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.avyvka.discipline_management.model.IdentifiableRecord;

import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CourseDto(
        UUID id,
        Integer number,
        Set<DisciplineDto> disciplines
) implements IdentifiableRecord<UUID> {
}