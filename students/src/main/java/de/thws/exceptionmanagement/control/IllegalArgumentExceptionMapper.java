package de.thws.exceptionmanagement.control;

import de.thws.exceptionmanagement.boundary.ErrorDTO;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public jakarta.ws.rs.core.Response toResponse(IllegalArgumentException exception) {
        ErrorDTO errorDTO = new ErrorDTO(
                exception.getMessage(),
                jakarta.ws.rs.core.Response.Status.BAD_REQUEST.getStatusCode(),
                "somewhere in space", // Path can be set if needed
                java.time.Instant.now().toString(),
                "hier ist was ganz komisches passiert..." // Details can be set if needed
        );

        return jakarta.ws.rs.core.Response
                .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                .entity(errorDTO)
                .build();
    }

}
