package de.thws.courses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CourseParticipant {

    @Id
    @GeneratedValue
    public Long id;

    public Double grade;

    @ManyToOne
    public Course course;
}
