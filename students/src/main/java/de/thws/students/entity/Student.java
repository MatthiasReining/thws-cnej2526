package de.thws.students.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import de.thws.courses.entity.CourseParticipant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@NamedQueries({
        @NamedQuery(name = Student.FIND_ALL, query = "SELECT s FROM Student s"),
        @NamedQuery(name = Student.FIND_BY_MATRICULATION_NUMBER, query = "SELECT s FROM Student s WHERE s.matriculationNumber = :"
                + Student.PARAM_MATRICULATION_NUMBER)
})
@Entity
public class Student {

    public static final String FIND_ALL = "Student.findAll";
    public static final String FIND_BY_MATRICULATION_NUMBER = "Student.findByMatriculationNumber";
    public static final String PARAM_MATRICULATION_NUMBER = "matriculationNumber";

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String matriculationNumber;
    private LocalDate enrollmentDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<CourseParticipant> courseParticiapant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", matriculationNumber='" + matriculationNumber + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }

    public List<CourseParticipant> getCourseParticiapant() {
        return courseParticiapant;
    }

    public void setCourseParticiapant(List<CourseParticipant> courseParticiapant) {
        this.courseParticiapant = courseParticiapant;
    }

}
