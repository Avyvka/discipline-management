package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.controller.support.AbstractCrudController;
import com.github.avyvka.discipline_management.model.dto.DisciplineDto;
import com.github.avyvka.discipline_management.service.support.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disciplines")
public class DisciplineRestController extends AbstractCrudController<DisciplineDto, UUID> {

    @Autowired
    public DisciplineRestController(CrudService<DisciplineDto, UUID> service) {
        super(service);
    }
}
