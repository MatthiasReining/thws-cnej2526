package de.thws.students.boundary;

import java.util.List;

import de.thws.students.control.StudentService;
import de.thws.students.entity.Student;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @POST
    @Path("/{id}/courses")
    public void addCourse(@PathParam("id") Long id, @Valid CourseParticiapantDTO courseParticiapantDTO) {

        System.out.println("Adding course participant to student with id: " + id);
        studentService.addCourseParticipation(id, courseParticiapantDTO);

    }

    @GET
    @Path("/get-all-example")
    public Response getAllStudentsExampe(@QueryParam("content") String content) {
        if ("small".equals(content)) {
            return Response.status(444)
                    .entity("This is a small content response")
                    .build();
        }
        return Response.status(333).entity(
                studentService.findAll().stream()
                        .map(this::toDTO)
                        .toList())
                .build();
    }

    @GET
    @Path("/{id}")
    public Student getStudentById(@PathParam("id") Long id) {
        return studentService.findById(id)
                .orElseThrow(() -> new NotFoundException("Student with id " + id + " not found"));

        // .map(this::toDTO)
        // .orElseThrow(() -> new NotFoundException("Student with id " + id + " not
        // found"));
    }

    // is this endpoint well designed? or better to move as query param in
    // /students?matriculationNumber=...
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
        student.setId(id);
        return this.toDTO(studentService.update(student));
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
        Student s = new Student();
        s.setFirstName(dto.firstName());
        s.setLastName(dto.lastName());
        s.setEmail(dto.email());
        s.setMatriculationNumber(dto.matriculationNumber());
        s.setEnrollmentDate(dto.enrollmentDate());
        return s;
    }

    private Student fromUpdateDTO(UpdateStudentDTO dto) {
        Student s = new Student();
        s.setFirstName(dto.firstName());
        s.setLastName(dto.lastName());
        s.setEmail(dto.email());
        s.setMatriculationNumber(dto.matriculationNumber());
        s.setEnrollmentDate(dto.enrollmentDate());
        return s;
    }
}
