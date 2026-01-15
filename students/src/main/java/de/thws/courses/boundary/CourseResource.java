package de.thws.courses.boundary;

import java.util.List;

import de.thws.courses.entity.Course;
import de.thws.courses.entity.CourseParticipant;
import de.thws.students.boundary.StudentDTO;
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
    public List<CourseParticipant> getAllStudentsByCourses(@PathParam("courseId") Long courseId) {

        Course course = em.find(Course.class, courseId);

        var courseParticipants = course.courseParticipants;

        return courseParticipants;
    }

}
