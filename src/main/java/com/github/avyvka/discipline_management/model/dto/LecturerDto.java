package com.github.avyvka.discipline_management.model.dto;

import com.github.avyvka.discipline_management.model.entity.Discipline;

import java.util.Set;

public record LecturerDto(
        String id,
        String firstName,
        String lastName,
        String middleName,
        int age,
        String academicTitle,
        Set<Discipline> disciplines
) {
}