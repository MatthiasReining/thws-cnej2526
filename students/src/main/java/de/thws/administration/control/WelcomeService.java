package de.thws.administration.control;

import de.thws.students.boundary.dto.StudentDTO;
import jakarta.enterprise.event.ObservesAsync;

public class WelcomeService {

    public void writeWelcomeMessage(@ObservesAsync StudentDTO student) throws InterruptedException {
        System.out.println("Prepare messsage for student: " + student.firstName());
        Thread.sleep(2000); // simulate some work
        System.out.println("Welcome, " + student.firstName() + ", to THWS!");
    }
}
