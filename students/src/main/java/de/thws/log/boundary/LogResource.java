package de.thws.log.boundary;

import java.util.List;

import de.thws.log.entity.LogData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/logs")
public class LogResource {

    @GET
    public List<LogData> getAllLogs() {
        return LogData.findAll().list();
    }

    @Transactional
    @POST
    public void createLog(@Valid LogData logData) {
        logData.persist();
    }

}
