package de.thws.courses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public Integer capacity;

}
