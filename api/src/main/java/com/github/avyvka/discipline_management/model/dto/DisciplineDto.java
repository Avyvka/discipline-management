package com.github.avyvka.discipline_management.model.dto;

import java.util.UUID;

public record DisciplineDto(
        UUID id,
        String name,
        String description,
        LecturerDto lecturer
) implements IdentifiableEntityDto<UUID> {
}