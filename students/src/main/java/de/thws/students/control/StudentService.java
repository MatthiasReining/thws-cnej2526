package de.thws.students.control;

import de.thws.students.entity.Student;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class StudentService {

    private static final Map<Long, Student> STUDENTS = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

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
        ID_GENERATOR.set(6);
    }

    public Collection<Student> findAll() {
        return STUDENTS.values();
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(STUDENTS.get(id));
    }

    public Optional<Student> findByMatriculationNumber(String matriculationNumber) {
        return STUDENTS.values().stream()
                .filter(s -> s.getMatriculationNumber().equals(matriculationNumber))
                .findFirst();
    }

    public Student create(Student student) {
        if (findByMatriculationNumber(student.getMatriculationNumber()).isPresent()) {
            throw new IllegalArgumentException("Student with matriculation number " +
                    student.getMatriculationNumber() + " already exists");
        }
        Long id = ID_GENERATOR.getAndIncrement();
        student.setId(id);
        STUDENTS.put(id, student);
        return student;
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
        ID_GENERATOR.set(1);
    }
}
