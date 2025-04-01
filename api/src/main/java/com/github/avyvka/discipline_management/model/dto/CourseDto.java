package com.github.avyvka.discipline_management.model.dto;

import java.util.Set;
import java.util.UUID;

public record CourseDto(
        UUID id,
        Integer number,
        Set<DisciplineDto> disciplines
) implements IdentifiableEntityDto<UUID> {
}