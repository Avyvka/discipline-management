package com.github.avyvka.discipline_management.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface EntityDtoMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    E update(@MappingTarget E entity, D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E partialUpdate(@MappingTarget E entity, D dto);
}
