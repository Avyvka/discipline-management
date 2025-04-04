package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.model.dto.LecturerDto;
import com.github.avyvka.discipline_management.model.entity.Discipline;
import com.github.avyvka.discipline_management.model.entity.Lecturer;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        disableSubMappingMethodsGeneration = true
)
public interface LecturerMapper extends EntityDtoMapper<Lecturer, LecturerDto> {

    @Override
    @Mapping(target = "disciplines", ignore = true)
    Lecturer toEntity(LecturerDto dto);

    @Override
    @Mapping(target = "disciplines", ignore = true)
    LecturerDto toDtoLazy(Lecturer entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disciplines", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Lecturer update(@MappingTarget Lecturer entity, LecturerDto dto);

    @Override
    @Mapping(target = "disciplines", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Lecturer partialUpdate(@MappingTarget Lecturer entity, LecturerDto dto);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "lecturer", ignore = true)
    DisciplineDto toDisciplineDto(Discipline entity);
}