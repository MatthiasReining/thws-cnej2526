package de.thws.students.control;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.thws.courses.entity.Course;
import de.thws.courses.entity.CourseParticipant;
import de.thws.students.boundary.dto.CreateCourseParticiapantDTO;
import de.thws.students.entity.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StudentService {

    @PersistenceContext
    EntityManager em;

    public Collection<Student> findAll() {

        List<Student> students = em.createNamedQuery(Student.FIND_ALL, Student.class).getResultList();

        return students;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    public Optional<Student> findByMatriculationNumber(String matriculationNumber) {

        List<Student> students = em
                .createNamedQuery(Student.FIND_BY_MATRICULATION_NUMBER, Student.class)
                .setParameter(Student.PARAM_MATRICULATION_NUMBER, matriculationNumber)
                .getResultList();

        if (students.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(students.get(0));
        }

    }

    @Transactional
    public Student create(Student student) {

        // Ipmlemnt as DB contraint or as business logic in Java ???
        // skipped here
        // if (findByMatriculationNumber(student.getMatriculationNumber()).isPresent())
        // {
        // throw new WebApplicationException("Student with matriculation number " +
        // student.getMatriculationNumber() + " already exists");
        // }

        return em.merge(student);
    }

    @Transactional
    public Student update(Student updatedStudent) {

        // TODO check the id! It's not guaranteed that the id is correct.

        return em.merge(updatedStudent);

    }

    @Transactional
    public boolean delete(Long id) {
        Student s = em.find(Student.class, id);
        if (s == null) {
            return false;
        }
        em.remove(s);
        return true;
    }

    @Transactional
    public void addCourseParticipation(Long studentId, CreateCourseParticiapantDTO courseParticiapantDTO) {

        Student student = em.find(Student.class, studentId);

        if (student == null) {
            // Example for built-in exception with ExceptionMapper (see
            // IllegalArgumentExceptionMapper)
            throw new IllegalArgumentException("Student with id " + studentId + " not found");
        }

        CourseParticipant cp = new CourseParticipant();
        Course course = em.find(Course.class, courseParticiapantDTO.courseId());
        if (course == null) {
            // Example for custom exception
            throw new CourseNotFoundException(
                    "Course with id " + courseParticiapantDTO.courseId() + " not found");
        }

        cp.course = course;
        cp.grade = courseParticiapantDTO.grade();

        student.getCourseParticiapant().add(cp);

    }

}
