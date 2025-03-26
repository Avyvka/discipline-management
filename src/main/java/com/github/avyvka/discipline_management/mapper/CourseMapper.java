package com.github.avyvka.discipline_management.mapper;

import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    Course toEntity(CourseDto dto);

    CourseDto toDto(Course entity);

    Course update(@MappingTarget Course entity, CourseDto dto);
}