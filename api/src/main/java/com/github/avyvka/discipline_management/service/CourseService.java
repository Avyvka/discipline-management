package com.github.avyvka.discipline_management.service;

import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.service.support.CrudService;

import java.util.UUID;

public interface CourseService extends CrudService<CourseDto, UUID> {
}
