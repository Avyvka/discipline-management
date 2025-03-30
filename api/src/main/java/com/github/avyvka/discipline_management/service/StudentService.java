package com.github.avyvka.discipline_management.service;

import com.github.avyvka.discipline_management.model.dto.StudentDto;
import com.github.avyvka.discipline_management.service.support.CrudService;

import java.util.UUID;

public interface StudentService extends CrudService<StudentDto, UUID> {
}
