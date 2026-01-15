package de.thws.students.boundary.dto;

import jakarta.validation.constraints.NotNull;

public record CreateCourseParticiapantDTO(Double grade, @NotNull Long courseId) {
}
