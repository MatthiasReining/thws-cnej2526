package de.thws.students.control;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class CourseNotFoundException extends WebApplicationException {

    public CourseNotFoundException(String message) {
        super(message, Status.NOT_FOUND);
    }

}
