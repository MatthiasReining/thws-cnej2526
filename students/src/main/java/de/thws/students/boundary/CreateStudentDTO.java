package de.thws.students.boundary;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Data Transfer Object for creating a new Student.
 */
public record CreateStudentDTO(
        /** First name of the new student. */
        @NotBlank String firstName,

        @NotBlank(message = "Last name is required") String lastName,

        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,

        @NotBlank(message = "Matriculation number is required") @Pattern(regexp = "^[0-9]{7}$", message = "Matriculation number must be 7 digits") String matriculationNumber,

        @NotNull(message = "Enrollment date is required") LocalDate enrollmentDate) {
}
