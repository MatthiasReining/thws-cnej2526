package de.thws.courses.boundary;

import java.util.ArrayList;
import java.util.List;

import de.thws.courses.boundary.dto.CourseDTO;
import de.thws.courses.boundary.dto.CourseParticiapantDTO;
import de.thws.courses.entity.Course;
import de.thws.courses.entity.CourseParticipant;
import de.thws.students.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/courses")
public class CourseResource {

    @PersistenceContext
    EntityManager em;

    @GET
    public List<CourseDTO> getAllCourses() {

        var courses = em.createQuery("SELECT c FROM Course c", Course.class).getResultList();

        return courses.stream()
                .map(c -> new CourseDTO(c.id, c.name, c.capacity))
                .toList();
    }

    @GET
    @Path("/{courseId}/students")
    public List<CourseParticiapantDTO> getAllStudentsByCourses(@PathParam("courseId") Long courseId) {

        List<CourseParticipant> cps = em
                .createQuery("SELECT cp FROM CourseParticipant cp WHERE cp.course.id = :courseId",
                        CourseParticipant.class)
                .setParameter("courseId", courseId)
                .getResultList();

        List<CourseParticiapantDTO> result = new ArrayList<>();
        for (CourseParticipant cp : cps) {

            // every iteration is a dedicated select -> optimize statentment! (see
            // README.md)
            Student s = cp.student;
            var courseParticipantDTO = new CourseParticiapantDTO(s.getId(),
                    s.getFirstName(), s.getLastName(), s.getEmail(), s.getMatriculationNumber(), cp.grade);
            result.add(courseParticipantDTO);
        }

        return result;
    }

}
