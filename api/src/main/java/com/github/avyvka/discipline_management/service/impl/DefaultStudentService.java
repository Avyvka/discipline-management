package com.github.avyvka.discipline_management.service.impl;

import com.github.avyvka.discipline_management.mapper.EntityDtoMapper;
import com.github.avyvka.discipline_management.model.dto.StudentDto;
import com.github.avyvka.discipline_management.model.entity.Student;
import com.github.avyvka.discipline_management.service.StudentService;
import com.github.avyvka.discipline_management.service.support.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultStudentService extends AbstractCrudService<Student, StudentDto, UUID>
        implements StudentService {
    @Autowired
    public DefaultStudentService(
            JpaRepository<Student, UUID> repository,
            EntityDtoMapper<Student, StudentDto> mapper
    ) {
        super(repository, mapper);
    }
}
