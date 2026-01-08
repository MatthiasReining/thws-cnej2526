package de.thws.students.control;

import de.thws.students.entity.Student;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentServiceTest {

    @Inject
    StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService.clear();
    }

    @AfterEach
    void tearDown() {
        studentService.clear();
    }

    @Test
    void testCreateStudent() {
        Student student = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");

        Student created = studentService.create(student);

        assertNotNull(created.getId());
        assertEquals("John", created.getFirstName());
        assertEquals("Doe", created.getLastName());
        assertEquals("john.doe@example.com", created.getEmail());
        assertEquals("1234567", created.getMatriculationNumber());
    }

    @Test
    void testCreateStudentWithDuplicateMatriculationNumber() {
        Student student1 = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        studentService.create(student1);

        Student student2 = createTestStudent("Jane", "Smith", "jane.smith@example.com", "1234567");

        assertThrows(IllegalArgumentException.class, () -> studentService.create(student2));
    }

    @Test
    void testFindAll() {
        Student student1 = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        Student student2 = createTestStudent("Jane", "Smith", "jane.smith@example.com", "2345678");

        studentService.create(student1);
        studentService.create(student2);

        Collection<Student> students = studentService.findAll();

        assertEquals(2, students.size());
    }

    @Test
    void testFindById() {
        Student student = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        Student created = studentService.create(student);

        Optional<Student> found = studentService.findById(created.getId());

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Student> found = studentService.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    void testFindByMatriculationNumber() {
        Student student = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        studentService.create(student);

        Optional<Student> found = studentService.findByMatriculationNumber("1234567");

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    void testUpdateStudent() {
        Student student = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        Student created = studentService.create(student);

        Student updated = createTestStudent("John", "Smith", "john.smith@example.com", "1234567");
        Optional<Student> result = studentService.update(created.getId(), updated);

        assertTrue(result.isPresent());
        assertEquals("Smith", result.get().getLastName());
        assertEquals("john.smith@example.com", result.get().getEmail());
    }

    @Test
    void testUpdateStudentNotFound() {
        Student student = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");

        Optional<Student> result = studentService.update(999L, student);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateStudentWithDuplicateMatriculationNumber() {
        Student student1 = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        Student student2 = createTestStudent("Jane", "Smith", "jane.smith@example.com", "2345678");

        Student created1 = studentService.create(student1);
        Student created2 = studentService.create(student2);

        Student updated = createTestStudent("John", "Doe", "john.doe@example.com", "2345678");

        assertThrows(IllegalArgumentException.class,
                () -> studentService.update(created1.getId(), updated));
    }

    @Test
    void testDeleteStudent() {
        Student student = createTestStudent("John", "Doe", "john.doe@example.com", "1234567");
        Student created = studentService.create(student);

        boolean deleted = studentService.delete(created.getId());

        assertTrue(deleted);
        assertFalse(studentService.findById(created.getId()).isPresent());
    }

    @Test
    void testDeleteStudentNotFound() {
        boolean deleted = studentService.delete(999L);

        assertFalse(deleted);
    }

    private Student createTestStudent(String firstName, String lastName, String email, String matriculationNumber) {
        return new Student(null, firstName, lastName, email, matriculationNumber, LocalDate.now());
    }
}
