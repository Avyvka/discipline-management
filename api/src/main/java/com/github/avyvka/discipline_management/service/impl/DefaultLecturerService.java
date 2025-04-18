package com.github.avyvka.discipline_management.service.impl;

import com.github.avyvka.discipline_management.mapper.EntityDtoMapper;
import com.github.avyvka.discipline_management.model.dto.LecturerDto;
import com.github.avyvka.discipline_management.model.entity.Lecturer;
import com.github.avyvka.discipline_management.service.LecturerService;
import com.github.avyvka.discipline_management.service.support.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultLecturerService extends AbstractCrudService<Lecturer, LecturerDto, UUID>
        implements LecturerService {
    @Autowired
    public DefaultLecturerService(
            JpaRepository<Lecturer, UUID> repository,
            EntityDtoMapper<Lecturer, LecturerDto> mapper
    ) {
        super(repository, mapper);
    }
}
