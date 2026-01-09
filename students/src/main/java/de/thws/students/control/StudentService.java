package de.thws.students.control;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import de.thws.students.entity.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StudentService {

    @PersistenceContext
    EntityManager em;

    private static final Map<Long, Student> STUDENTS = new ConcurrentHashMap<>();

    static {
        // Initialize with 5 sample students
        STUDENTS.put(1L,
                new Student(1L, "Max", "Mustermann", "max.mustermann@thws.de", "1234567", LocalDate.of(2023, 10, 1)));
        STUDENTS.put(2L,
                new Student(2L, "Anna", "Schmidt", "anna.schmidt@thws.de", "2345678", LocalDate.of(2023, 10, 1)));
        STUDENTS.put(3L,
                new Student(3L, "Thomas", "Müller", "thomas.mueller@thws.de", "3456789", LocalDate.of(2024, 3, 15)));
        STUDENTS.put(4L,
                new Student(4L, "Sarah", "Weber", "sarah.weber@thws.de", "4567890", LocalDate.of(2024, 3, 15)));
        STUDENTS.put(5L,
                new Student(5L, "Michael", "Fischer", "michael.fischer@thws.de", "5678901", LocalDate.of(2024, 10, 1)));

    }

    public Collection<Student> findAll() {

        // JPQL -> SQL "SELECT * FROM Student s" -> JPQL Select s from Student s
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();

        return students;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    public Optional<Student> findByMatriculationNumber(String matriculationNumber) {
        return STUDENTS.values().stream()
                .filter(s -> s.getMatriculationNumber().equals(matriculationNumber))
                .findFirst();
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

    public Optional<Student> update(Long id, Student updatedStudent) {
        if (!STUDENTS.containsKey(id)) {
            return Optional.empty();
        }

        // Check if matriculation number is used by another student
        Optional<Student> existing = findByMatriculationNumber(updatedStudent.getMatriculationNumber());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new IllegalArgumentException("Student with matriculation number " +
                    updatedStudent.getMatriculationNumber() + " already exists");
        }

        updatedStudent.setId(id);
        STUDENTS.put(id, updatedStudent);
        return Optional.of(updatedStudent);
    }

    public boolean delete(Long id) {
        return STUDENTS.remove(id) != null;
    }

    // For testing purposes
    public void clear() {
        STUDENTS.clear();

    }
}
