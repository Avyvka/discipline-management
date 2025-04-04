package com.github.avyvka.discipline_management.service.impl;

import com.github.avyvka.discipline_management.mapper.EntityDtoMapper;
import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.model.entity.Discipline;
import com.github.avyvka.discipline_management.service.DisciplineService;
import com.github.avyvka.discipline_management.service.support.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultDisciplineService extends AbstractCrudService<Discipline, DisciplineDto, UUID>
        implements DisciplineService {
    @Autowired
    public DefaultDisciplineService(
            JpaRepository<Discipline, UUID> repository,
            EntityDtoMapper<Discipline, DisciplineDto> mapper
    ) {
        super(repository, mapper);
    }
}
