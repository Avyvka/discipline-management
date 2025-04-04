package com.github.avyvka.discipline_management.service;

import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.service.support.CrudService;

import java.util.UUID;

public interface DisciplineService extends CrudService<DisciplineDto, UUID> {
}
