package de.thws.students.boundary.dto;

import java.time.LocalDate;

public record StudentDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String matriculationNumber,
        LocalDate enrollmentDate) {
}
