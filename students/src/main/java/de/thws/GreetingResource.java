package de.thws;

import io.quarkus.Generated;
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

    @GET
    @Path("complex")
    public Hello complexHello() {
        return new Hello("Hello from Quarkus REST from THWS");
    }
}
