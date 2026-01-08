package de.thws.students.boundary;

import de.thws.students.control.StudentService;
import de.thws.students.entity.Student;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentsResource {

    @Inject
    StudentService studentService;

    @GET
    public List<StudentDTO> getAllStudents() {
        return studentService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GET
    @Path("/{id}")
    public StudentDTO getStudentById(@PathParam("id") Long id) {
        return studentService.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Student with id " + id + " not found"));
    }

    @GET
    @Path("/matriculation/{matriculationNumber}")
    public StudentDTO getStudentByMatriculationNumber(@PathParam("matriculationNumber") String matriculationNumber) {
        return studentService.findByMatriculationNumber(matriculationNumber)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Student with matriculation number " +
                        matriculationNumber + " not found"));
    }

    @POST
    public StudentDTO createStudent(@Valid CreateStudentDTO createDTO) {
        Student student = fromCreateDTO(createDTO);
        Student created = studentService.create(student);
        return toDTO(created);
    }

    @PUT
    @Path("/{id}")
    public StudentDTO updateStudent(@PathParam("id") Long id, @Valid UpdateStudentDTO updateDTO) {
        Student student = fromUpdateDTO(updateDTO);
        return studentService.update(id, student)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Student with id " + id + " not found"));
    }

    @DELETE
    @Path("/{id}")
    public void deleteStudent(@PathParam("id") Long id) {
        boolean deleted = studentService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Student with id " + id + " not found");
        }
    }

    private StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMatriculationNumber(),
                student.getEnrollmentDate());
    }

    private Student fromCreateDTO(CreateStudentDTO dto) {
        return new Student(
                null,
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.matriculationNumber(),
                dto.enrollmentDate());
    }

    private Student fromUpdateDTO(UpdateStudentDTO dto) {
        return new Student(
                null,
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.matriculationNumber(),
                dto.enrollmentDate());
    }
}
