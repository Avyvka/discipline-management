package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.model.dto.LecturerDto;
import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.model.entity.Discipline;
import com.github.avyvka.discipline_management.model.entity.Lecturer;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        disableSubMappingMethodsGeneration = true
)
public interface DisciplineMapper extends EntityDtoMapper<Discipline, DisciplineDto> {

    @Override
    Discipline toEntity(DisciplineDto dto);

    @Override
    DisciplineDto toDto(Discipline entity);

    @Override
    DisciplineDto toDtoLazy(Discipline entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Discipline update(@MappingTarget Discipline entity, DisciplineDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Discipline partialUpdate(@MappingTarget Discipline entity, DisciplineDto dto);

    Lecturer toLecturer(LecturerDto dto);

    @Mapping(target = "disciplines", ignore = true)
    LecturerDto toLecturerDto(Lecturer entity);

    Course toCourse(CourseDto dto);

    @Mapping(target = "disciplines", ignore = true)
    CourseDto toCourseDto(Course entity);
}