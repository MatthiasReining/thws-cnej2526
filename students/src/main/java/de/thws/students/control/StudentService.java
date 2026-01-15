package de.thws.students.control;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.thws.courses.entity.Course;
import de.thws.courses.entity.CourseParticipant;
import de.thws.log.control.LogService;
import de.thws.students.boundary.dto.CreateCourseParticiapantDTO;
import de.thws.students.boundary.dto.StudentDTO;
import de.thws.students.entity.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StudentService {

    @PersistenceContext
    EntityManager em;

    @Inject
    LogService logService;

    @Inject
    Event<StudentDTO> studentCreatedEvent;

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

        var s = em.merge(student);

        var dto = new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMatriculationNumber(),
                student.getEnrollmentDate());

        studentCreatedEvent.fireAsync(dto);

        return s;
    }

    // REQREUIED - default behaviour
    @Transactional(Transactional.TxType.REQUIRED)
    public Student createTxExample(Student student) {

        Student s = em.merge(student);

        try {

            logService.log("Student " + student.getFirstName() + " created");
        } catch (Exception e) {
            // exception from logService.log is thrown and managed here
            System.out.println("Logging failed: " + e.getMessage());
            // ignore log exeption problems
        }

        // Student is created within this transaciont (REQUIRED)
        // Log Entity Tx is rolled back due to exception in LogService (REQUIRES_NEW)
        return s;
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
