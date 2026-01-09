package de.thws.students.control;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

        // JPQL -> SQL "SELECT * FROM Student s" -> JPQL Select s from Student s
        List<Student> students = em.createQuery("SELECT x FROM Student x", Student.class)
                .getResultList();

        return students;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    public Optional<Student> findByMatriculationNumber(String matriculationNumber) {

        List<Student> students = em
                .createQuery("SELECT s FROM Student s WHERE s.matriculationNumber = :matriculationNumber",
                        Student.class)
                .setParameter("matriculationNumber", matriculationNumber)
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

}
