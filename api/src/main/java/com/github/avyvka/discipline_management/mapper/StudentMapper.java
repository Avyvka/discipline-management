package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.model.dto.StudentDto;
import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        disableSubMappingMethodsGeneration = true
)
public interface StudentMapper extends EntityDtoMapper<Student, StudentDto> {

    @Mapping(target = "disciplines", ignore = true)
    Course toDto(CourseDto dto);

    @Mapping(target = "disciplines", ignore = true)
    CourseDto toDto(Course entity);
}