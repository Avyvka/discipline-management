package com.github.avyvka.discipline_management.model.dto;

import com.github.avyvka.discipline_management.model.entity.Discipline;

import java.util.Set;
import java.util.UUID;

public record LecturerDto(
        UUID id,
        String firstName,
        String lastName,
        String middleName,
        int age,
        String academicTitle,
        Set<Discipline> disciplines
) implements IdentifiableEntityDto<UUID> {
}