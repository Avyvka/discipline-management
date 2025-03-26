package com.github.avyvka.discipline_management.model.dto;

public record DisciplineDto(
        String id,
        String name,
        String description,
        LecturerDto lecturer
) {
}