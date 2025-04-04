package com.github.avyvka.discipline_management.model;

public interface IdentifiableRecord<ID> extends Identifiable<ID> {

    ID id();

    default ID getId() {
        return id();
    }
}
