package de.thws.students.boundary;

import java.time.LocalDate;

public record StudentDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String matriculationNumber,
        LocalDate enrollmentDate) {
}
