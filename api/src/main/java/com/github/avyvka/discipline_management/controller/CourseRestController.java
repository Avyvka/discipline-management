package com.github.avyvka.discipline_management.controller;

import com.github.avyvka.discipline_management.controller.support.AbstractCrudController;
import com.github.avyvka.discipline_management.model.dto.CourseDto;
import com.github.avyvka.discipline_management.service.support.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseRestController extends AbstractCrudController<CourseDto, UUID> {

    @Autowired
    public CourseRestController(CrudService<CourseDto, UUID> service) {
        super(service);
    }
}
