package de.thws.courses.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Course {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public Integer capacity;

    @OneToMany(mappedBy = "course")
    public List<CourseParticipant> courseParticipants;

}
