package de.thws.students.boundary;

import jakarta.validation.constraints.NotNull;

public record CourseParticiapantDTO(Double grade, @NotNull Long courseId) {
}
