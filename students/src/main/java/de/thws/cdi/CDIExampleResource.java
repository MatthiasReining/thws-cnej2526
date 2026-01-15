package de.thws.cdi;

import java.util.Date;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/cdi-examples")
public class CDIExampleResource {

    @Inject
    Bean1 bean1;

    @Inject
    BusinessLogic businessLogic;

    @Inject
    @MessageWorld
    String messageWorld;

    @Inject
    String message;

    @Inject
    Logger logger;

    @THWSPerformanceMeasurement
    @GET
    public String cdiExample1() {

        logger.log("CDI Example Resource accessed.");

        return "CDI Example Message: " + message + " | " + messageWorld;
    }

    @GET
    @Path("bean1")
    @THWSPerformanceMeasurement
    public String bean1Example() {

        bean1.setContent("data: " + new Date());
        try {
            // Simulate some processing time
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bean1.getContent();
    }

    @GET
    @Path("bean1-read")
    public String bean1ReadExample() {
        return bean1.getContent();
    }

    @GET
    @Path("interceptor-example")
    public String interceptorExample1(@QueryParam("input") String input) {
        return "Content: " + businessLogic.process(input);
    }

    @GET
    @Path("event-example")
    public String eventExample(@QueryParam("input") String input) {
        return businessLogic.logic2(input);
    }
}
