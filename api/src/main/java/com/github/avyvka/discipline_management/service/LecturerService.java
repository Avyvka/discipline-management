package com.github.avyvka.discipline_management.service;

import com.github.avyvka.discipline_management.model.dto.LecturerDto;
import com.github.avyvka.discipline_management.service.support.CrudService;

import java.util.UUID;

public interface LecturerService extends CrudService<LecturerDto, UUID> {
}
