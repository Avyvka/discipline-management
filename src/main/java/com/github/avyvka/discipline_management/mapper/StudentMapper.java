package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.StudentDto;
import com.github.avyvka.discipline_management.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    Student toEntity(StudentDto dto);

    StudentDto toDto(Student entity);

    Student update(@MappingTarget Student entity, StudentDto dto);
}