package com.github.avyvka.discipline_management.model.dto;

import java.util.Set;

public record CourseDto(
        String id,
        int number,
        Set<DisciplineDto> disciplines
) {
}