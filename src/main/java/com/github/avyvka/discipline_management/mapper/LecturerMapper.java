package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.LecturerDto;
import com.github.avyvka.discipline_management.model.entity.Lecturer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LecturerMapper {

    Lecturer toEntity(LecturerDto dto);

    LecturerDto toDto(Lecturer entity);

    Lecturer update(@MappingTarget Lecturer entity, LecturerDto dto);
}