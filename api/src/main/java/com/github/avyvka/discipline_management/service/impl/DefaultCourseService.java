package com.github.avyvka.discipline_management.service.impl;

import com.github.avyvka.discipline_management.mapper.EntityDtoMapper;
import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.model.entity.Course;
import com.github.avyvka.discipline_management.service.CourseService;
import com.github.avyvka.discipline_management.service.support.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultCourseService extends AbstractCrudService<Course, CourseDto, UUID>
        implements CourseService {
    @Autowired
    public DefaultCourseService(
            JpaRepository<Course, UUID> repository,
            EntityDtoMapper<Course, CourseDto> mapper
    ) {
        super(repository, mapper);
    }
}
