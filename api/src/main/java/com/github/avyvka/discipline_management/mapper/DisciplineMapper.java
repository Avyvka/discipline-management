package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.model.entity.Discipline;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DisciplineMapper extends EntityDtoMapper<Discipline, DisciplineDto> {
}