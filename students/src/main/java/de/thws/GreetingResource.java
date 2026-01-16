package de.thws;

import org.eclipse.microprofile.openapi.annotations.Operation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST from THWS";
    }

    /**
     * Not a simple 'hello world', just a complex greeting
     * // failure : DRY (dublicated code comment)
     * 
     * @return Hell object
     */
    @GET
    @Path("complex")
    @Operation(summary = "Complex Greetings", description = "Not a simple 'hello world', just a complex greeting")
    public Hello complexHello() {
        return new Hello("Hello from Quarkus REST from THWS");
    }
}
