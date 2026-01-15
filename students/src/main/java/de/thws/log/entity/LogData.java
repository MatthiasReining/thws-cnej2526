package de.thws.log.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class LogData extends PanacheEntity {

    @NotBlank
    public String message;
}
