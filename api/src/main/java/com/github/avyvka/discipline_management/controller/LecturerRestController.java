package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.controller.support.AbstractCrudController;
import com.github.avyvka.discipline_management.model.dto.LecturerDto;
import com.github.avyvka.discipline_management.service.support.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerRestController extends AbstractCrudController<LecturerDto, UUID> {

    @Autowired
    public LecturerRestController(CrudService<LecturerDto, UUID> service) {
        super(service);
    }
}
