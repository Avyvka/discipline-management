package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.model.entity.Discipline;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        disableSubMappingMethodsGeneration = true
)
public interface CourseMapper extends EntityDtoMapper<Course, CourseDto> {

    @Override
    @Mapping(target = "disciplines", ignore = true)
    Course toEntity(CourseDto dto);

    @Override
    @Mapping(target = "disciplines", ignore = true)
    CourseDto toDtoLazy(Course entity);

    @Override
    @Mapping(target = "disciplines", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Course update(@MappingTarget Course entity, CourseDto dto);

    @Override
    @Mapping(target = "disciplines", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course partialUpdate(@MappingTarget Course entity, CourseDto dto);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "lecturer", ignore = true)
    DisciplineDto toDisciplineDto(Discipline entity);
}