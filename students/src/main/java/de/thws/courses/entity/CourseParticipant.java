package de.thws.courses.entity;

import de.thws.students.entity.Student;
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

    @ManyToOne
    public Student student;
}
