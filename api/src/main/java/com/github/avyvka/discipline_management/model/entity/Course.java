package com.github.avyvka.discipline_management.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends IdentifiableEntity {

    @Column(nullable = false)
    private int number;

    @ManyToMany
    @JoinTable(
            name = "course_discipline",
            joinColumns = @JoinColumn(name = "course_id", nullable = false, unique = true),
            inverseJoinColumns = @JoinColumn(name = "discipline_id", nullable = false, unique = true)
    )
    private Set<Discipline> disciplines;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
}