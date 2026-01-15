package de.thws.courses.boundary.dto;

public record CourseParticiapantDTO(
                Long studentId,
                String firstName,
                String lastName,
                String email,
                String matriculationNumber,
                Double grade) {

}
