package com.github.avyvka.discipline_management.model.entity;

import jakarta.persistence.*;

@Entity
public class Student extends Person {

    @Column(name = "study_group")
    private String group;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}