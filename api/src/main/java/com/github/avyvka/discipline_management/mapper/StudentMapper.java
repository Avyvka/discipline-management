package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.model.dto.StudentDto;
import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.model.entity.Student;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        disableSubMappingMethodsGeneration = true
)
public interface StudentMapper extends EntityDtoMapper<Student, StudentDto> {

    @Override
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Student update(@MappingTarget Student entity, StudentDto dto);

    @Mapping(target = "disciplines", ignore = true)
    Course toDto(CourseDto dto);

    @Mapping(target = "disciplines", ignore = true)
    CourseDto toDto(Course entity);
}